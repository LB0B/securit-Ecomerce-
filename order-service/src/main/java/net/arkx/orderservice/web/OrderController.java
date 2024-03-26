package net.arkx.orderservice.web;

import jakarta.ws.rs.Path;
import net.arkx.orderservice.DTOs.OrderDto;
import net.arkx.orderservice.entities.Order;
import net.arkx.orderservice.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> getOrders(){
        return orderService.getAllOrders();
    }
    @GetMapping("{id}")
    public Order getOrderById(@PathVariable long id) throws Exception {
        return orderService.getOrderById(id);
    }
//    @PostMapping
    public void addOrder(@RequestBody Order order){
        orderService.addOrder(order);
    }
    @DeleteMapping
    public void deleteAllOrders(){
        orderService.deleteAllOrders();
    }
    @DeleteMapping("{id}")
    public void deleteOrderById(@PathVariable long id) throws Exception {
        orderService.deleteOrderById(id);
    }
    @PutMapping("{id}")
    public void updateOrder(@PathVariable long id, @RequestBody OrderDto orderDto) throws Exception {
        orderService.updateOder(id, orderDto);
    }
    @PostMapping("{id}/coupon")
    public void applyCoupon(@PathVariable long id, @RequestBody String couponCode) throws Exception {
        orderService.couponApplication(id, couponCode);
    }
    @GetMapping("status")
    public List<Order> findOrderByStatus(@RequestParam String status){
        return orderService.findOrderByStatus(status);
    }
}
