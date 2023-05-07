package net.nvsoftware.iOrderService.service;

import lombok.extern.log4j.Log4j2;
import net.nvsoftware.iOrderService.entity.OrderEntity;
import net.nvsoftware.iOrderService.model.OrderRequest;
import net.nvsoftware.iOrderService.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;
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
        // CALL PaymentService to charge, if Success, mark order COMPLETED, else CANCELLED

//        log.info("OrderService: Did placeOrder with orderId: " + orderEntity.getOrderId());
        return orderEntity.getOrderId();
    }
}
