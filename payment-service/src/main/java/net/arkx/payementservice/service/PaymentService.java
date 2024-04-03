package net.arkx.payementservice.service;

import jakarta.transaction.Transactional;
import net.arkx.payementservice.entities.Payment;
import net.arkx.payementservice.entities.PaymentMethod;
import net.arkx.payementservice.enums.StatusPayement;
import net.arkx.payementservice.exceptions.entityExceptions.EntityIdNullException;
import net.arkx.payementservice.exceptions.entityExceptions.EntityNotFoundException;
import net.arkx.payementservice.exceptions.entityExceptions.SameEntityException;
import net.arkx.payementservice.exceptions.payments.AmountNotCorrectException;
import net.arkx.payementservice.exceptions.payments.PaymentStatusCompleteException;
import net.arkx.payementservice.model.Order;
import net.arkx.payementservice.orders.OrderRestClient;
import net.arkx.payementservice.repository.PaymentMethodRepository;
import net.arkx.payementservice.repository.PaymentRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final OrderRestClient orderRestClient;

    public PaymentService(PaymentRepository paymentRepository, PaymentMethodRepository paymentMethodRepository, OrderRestClient orderRestClient) {
        this.paymentRepository = paymentRepository;
        this.paymentMethodRepository = paymentMethodRepository;
        this.orderRestClient = orderRestClient;
    }

    // Get payment by id
    public Payment findPaymentById(Long id) {

        Payment payment = paymentRepository.findById(id).orElse(null);


        if (payment != null) {
            Order order = orderRestClient.findOrderById(payment.getOrderId());
            if (order != null) {
                payment.setOrder(order);
                return payment;
            } else {
                throw new EntityNotFoundException("Order not found for payment with id: " + id);
            }
        } else {
            throw new EntityNotFoundException("Payment not found for payment with id: " + id);
        }
    }


    //Add Payment
    @Transactional
    public void addPayment(Payment payment) {
        if (payment.getAmount() < 0) {
            throw new AmountNotCorrectException("Amount can not be negative: " + payment.getAmount());
        }
        if (payment.getPaymentMethod() == null) {
            throw new EntityIdNullException("Payment method can't be null");
        }
        if (payment.getOrderId() == null) {
            throw new EntityIdNullException("Order Id can't be Null");
        } else {
            //paymentMethodRepository.save(payment.getPaymentMethod());

             paymentRepository.save(payment);
        }
    }

    //Update Amount
    @Transactional
    public void updateAmountPayment(Long idPayment, Long newAmount) {
        Payment payment = paymentRepository.findById(idPayment).orElse(null);
        if (payment != null) {
            if (newAmount > 0) {
                payment.setAmount(newAmount);
                paymentRepository.save(payment);
            } else {
                throw new AmountNotCorrectException("Amount can not be negative: " + newAmount);
            }
        } else {
            throw new EntityNotFoundException("Payment not found for payment with id: " + idPayment);
        }
    }
    // Update Order Id
    @Transactional
    public void updateOrderId(Long idPayment, Long newOrderId){
        Payment payment = paymentRepository.findById(idPayment).orElse(null);
        if(payment!= null){
            if(newOrderId>0&& !payment.getOrderId().equals(newOrderId)){
                payment.setOrderId(newOrderId);
                paymentRepository.save(payment);
            } else if (payment.getOrderId().equals(newOrderId)) {
                throw new SameEntityException("Can't change the order Id by the same value");
            }
        }
        else{
            throw new EntityNotFoundException("Payment not found for payment with id: "+idPayment);
        }
    }
    //Update Payment Method Id
    @Transactional
    public void updatePaymentMethodId(Long idPayment, PaymentMethod newPaymentMethod) {
        Payment payment = paymentRepository.findById(idPayment)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found for payment with id: " + idPayment));

        PaymentMethod paymentMethod = paymentMethodRepository.findById(newPaymentMethod.getId())
                .orElseThrow(() -> new EntityNotFoundException("Can't find the Payment Method Id: " + newPaymentMethod.getId()));

        payment.setPaymentMethod(paymentMethod);
        paymentRepository.save(payment);
    }

    //Delete Payment by Id
    @Transactional
    public void deletePayment(Long id) {
        Payment paymentToDelete = paymentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Payment not found for payment with id: " + id));

        if (paymentToDelete.getStatus() == StatusPayement.COMPLETED) {
            throw new PaymentStatusCompleteException("Can't change the status of a Payment that has been completed");
        }

        paymentToDelete.setStatus(StatusPayement.CANCELED);
        paymentRepository.save(paymentToDelete);
    }
}
