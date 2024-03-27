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
        double price;
        List<Product> products = order.getProducts();
        for (Product product:products) {
            price = product.getPrice();
            if (product.isPromo()){
                double discount = product.getDiscount();
                price = price - price * discount / 100;
            }
            amount = amount + price;
        }
        Invoice invoice = new Invoice();
        invoice.setAmount(amount);
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
    //find order by status
    public List<Order> findOrderByStatus(String status){
        return orderRepository.findAllByStatus(status);
    }
    //coupon method
    public void couponApplication(long orderId, String couponCode) throws Exception {
        Optional<Coupon> optional = couponRepository.findByCode(couponCode);
        // check if the coupon exits
        if (optional.isPresent()){
            // get the coupon
            Coupon coupon = optional.get();
            //get the order
            Order order = getOrderById(orderId);
            //get the invoice from the order
            Invoice invoice = order.getInvoice();
            //get the amount
            double amount = invoice.getAmount();
            //get the discount
            double discount = coupon.getDiscount();
            //apply the discount
            amount = amount - amount*discount/100;
            invoice.setAmount(amount);
            order.setInvoice(invoice);
        }
        else{
            throw new Exception();
        }
    }
}
