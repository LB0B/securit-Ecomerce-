package net.arkx.orderservice.web;

import net.arkx.orderservice.service.OrderService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderItemController {
    private final OrderService orderService;

    public OrderItemController(OrderService orderService) {
        this.orderService = orderService;
    }
}
