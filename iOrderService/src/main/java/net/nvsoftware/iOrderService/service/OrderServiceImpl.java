package net.nvsoftware.iOrderService.service;

import lombok.extern.log4j.Log4j2;
import net.nvsoftware.iOrderService.entity.OrderEntity;
import net.nvsoftware.iOrderService.external.client.PaymentServiceFeignClient;
import net.nvsoftware.iOrderService.external.client.ProductServiceFeignClient;
import net.nvsoftware.iOrderService.model.OrderRequest;
import net.nvsoftware.iOrderService.model.OrderResponse;
import net.nvsoftware.iOrderService.model.PaymentRequest;
import net.nvsoftware.iOrderService.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductServiceFeignClient productServiceFeignClient;

    @Autowired
    private PaymentServiceFeignClient paymentServiceFeignClient;
    @Override
    public long placeOrder(OrderRequest orderRequest) {
        // Use OrderService(this) to create order entity with order status CREATED, save it to database


        log.info("OrderService: Start placeOrder CREATE order");

        OrderEntity orderEntity = OrderEntity.builder()
                .productId(orderRequest.getProductId())
                .orderQuantity(orderRequest.getOrderQuantity())
                .totalAmount(orderRequest.getTotalAmount())
                .orderStatus("CREATED")
                .paymentMode(orderRequest.getPaymentMode().name())
                .orderDate(Instant.now())
                .build();
        orderEntity = orderRepository.save(orderEntity);
        log.info("OrderService placeOrder -save to orderdb done");

        // CALL ProductService to check products, if ok, reduce quantity and
        log.info("ProductServiceFeignClient: reduceQuantity start");
        productServiceFeignClient.reduceQuantity(orderRequest.getProductId(),orderRequest.getOrderQuantity());
        log.info("ProductServiceFeignClient: reduceQuantity done");

        // CALL PaymentService to charge, if Success, mark order COMPLETED, else CANCELLED
        log.info("PaymentServiceFeignClient doPayment: start");
        PaymentRequest paymentRequest = PaymentRequest.builder()
                    .orderId(orderEntity.getOrderId())
                    .paymentMode(orderRequest.getPaymentMode())
                    .totalAmount(orderRequest.getOrderQuantity())
                    .build();
        String orderStatus = null;
        try {
            paymentServiceFeignClient.doPayment(paymentRequest);
            orderStatus = "PLACED";
        }
        catch (Exception e) {
            orderStatus = "PAYMENT_FAILED";
        }
        orderEntity.setOrderStatus(orderStatus);
        orderRepository.save(orderEntity);
        log.info("PaymentServiceFeignClient doPayment: done");
        return orderEntity.getOrderId();
    }

    @Override
    public OrderResponse getOrderDetailByOrderId(long orderId) {
        log.info("OrderService getOrderDetailByOrderId: start with orderId " + orderId);
        OrderEntity orderEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("OrderService getOrderDetailByOrderId NOT FOUND for: " + orderId));
        OrderResponse orderResponse = OrderResponse.builder()
                .orderId(orderEntity.getOrderId())
                .totalAmount(orderEntity.getTotalAmount())
                .orderDate(orderEntity.getOrderDate())
                .orderStatus(orderEntity.getOrderStatus())
                .build();
        log.info("OrderService getOrderDetailByOrderId: done");
        return orderResponse;
    }
}
