package net.nvsoftware.iOrderService.controller;

import lombok.extern.log4j.Log4j2;
import net.nvsoftware.iOrderService.model.OrderRequest;
import net.nvsoftware.iOrderService.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@Log4j2
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/placeOrder")
    public ResponseEntity<Long> placeOrder(@RequestBody OrderRequest orderRequest) {
        long orderId = orderService.placeOrder(orderRequest);
        log.info("Order placed with id: " + orderId);
        return new ResponseEntity<>(orderId, HttpStatus.OK);
    }
}
