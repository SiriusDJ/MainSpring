package net.nvsoftware.iOrderService.service;

import net.nvsoftware.iOrderService.model.OrderRequest;
import org.springframework.stereotype.Service;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);
}
