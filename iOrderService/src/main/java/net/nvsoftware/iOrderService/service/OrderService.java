package net.nvsoftware.iOrderService.service;

import net.nvsoftware.iOrderService.model.OrderRequest;
import net.nvsoftware.iOrderService.model.OrderResponse;
import org.springframework.stereotype.Service;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);

    OrderResponse getOrderDetailByOrderId(long orderId);
}
