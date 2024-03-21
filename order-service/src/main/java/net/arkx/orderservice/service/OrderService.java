package net.arkx.orderservice.service;

import net.arkx.orderservice.DTOs.OrderDto;
import net.arkx.orderservice.entities.Coupon;
import net.arkx.orderservice.entities.Invoice;
import net.arkx.orderservice.entities.Order;
import net.arkx.orderservice.model.Product;
import net.arkx.orderservice.repository.CouponRepository;
import net.arkx.orderservice.repository.InvoiceRepository;
import net.arkx.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final InvoiceRepository invoiceRepository;
    private final CouponRepository couponRepository;

    public OrderService(OrderRepository orderRepository, InvoiceRepository invoiceRepository, CouponRepository couponRepository) {
        this.orderRepository = orderRepository;
        this.invoiceRepository = invoiceRepository;
        this.couponRepository = couponRepository;
    }

    //Generate invoice with order creation
    public void addOrder(Order order) {
        double amount = 0;
        List<Product> products = order.getProducts();
        for (Product product:products) {
            amount = amount + product.getPrice();
        }
        Invoice invoice = new Invoice();
        invoice.setAmount(amount);
        invoice.setOrder(order);
        order.setInvoice(invoice);
        invoiceRepository.save(invoice);
        orderRepository.save(order);
    }
    //Get all orders
    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }
    //get order by id
    public Order getOrderById(long id) throws Exception {
        Optional<Order> optional = orderRepository.findById(id);
        if (optional.isPresent()){
            return optional.get();
        }
        else{
            throw new Exception();
        }
    }
    //delete all orders
    public void deleteAllOrders(){
        orderRepository.deleteAll();
    }
    //delete order by id
    public void deleteOrderById(long id) throws Exception {
        if (orderRepository.existsById(id)){
            orderRepository.deleteById(id);
        }
        else{
            throw new Exception();
        }
    }
    //update order
    public void updateOder(long id, OrderDto newOrder) throws Exception {
        Optional<Order> optional = orderRepository.findById(id);
        if (optional.isPresent()){
            Order order= optional.get();
            order.setStatus(newOrder.getStatus());
            orderRepository.save(order);
        }
        else{
            throw new Exception();
        }
    }
    //coupon method (in progress)
    public void couponApplication(Order order, Coupon couponEntered){

    }
}
