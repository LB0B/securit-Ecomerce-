package net.arkx.orderservice.web;

import jakarta.ws.rs.Path;
import net.arkx.orderservice.entities.Order;
import net.arkx.orderservice.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public List<Order> getUsers(){
        return orderService.getAll();
    }
}
