package net.arkx.payementservice.service;

import net.arkx.payementservice.entities.Payment;
import net.arkx.payementservice.exceptions.orders.OrderNotFoundException;
import net.arkx.payementservice.exceptions.payments.PaymentNotFoundException;
import net.arkx.payementservice.model.Order;
import net.arkx.payementservice.orders.OrderRestClient;
import net.arkx.payementservice.repository.PaymentRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderRestClient orderRestClient;

    public PaymentService(PaymentRepository paymentRepository, OrderRestClient orderRestClient) {
        this.paymentRepository = paymentRepository;
        this.orderRestClient = orderRestClient;
    }
    public Payment findPaymentById(Long id){
        Payment payment = paymentRepository.findById(id).orElse(null);
        if(payment!= null){
            Order order = orderRestClient.findOrderById(payment.getOrderId());

        if(order!=null){
            payment.setOrder(order);
            return payment;
        }else {
            throw new OrderNotFoundException("Order not found for payment with id: "+id);
        }}
        else {
            throw  new PaymentNotFoundException("Order not found for payment with id: "+id);
            }
    }
}
