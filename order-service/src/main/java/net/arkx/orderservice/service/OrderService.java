package net.arkx.orderservice.service;

import net.arkx.orderservice.entities.Order;
import net.arkx.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }
    public List<Order> getAll(){
        return orderRepository.findAll();
    }
}
