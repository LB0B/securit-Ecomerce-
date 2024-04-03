package net.arkx.payementservice.service;

import net.arkx.payementservice.entities.Payment;
import net.arkx.payementservice.entities.PaymentMethod;
import net.arkx.payementservice.exceptions.entityExceptions.EntityNotFoundException;
import net.arkx.payementservice.repository.PaymentMethodRepository;
import net.arkx.payementservice.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentMethodService {
    private final PaymentMethodRepository paymentMethodRepository;
    private final PaymentRepository paymentRepository;

    public PaymentMethodService(PaymentMethodRepository paymentMethodRepository, PaymentRepository paymentRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
        this.paymentRepository = paymentRepository;
    }
    //Add new Payment Method
    public PaymentMethod addPaymentMethod(PaymentMethod paymentMethod){
        return paymentMethodRepository.save(paymentMethod);
    }
    //Update Payment Method
    public PaymentMethod updatePaymentMethodName(Long idPaymentMethod, String newName){
        PaymentMethod paymentMethod = paymentMethodRepository.findById(idPaymentMethod)
                .orElseThrow(()->new EntityNotFoundException("Can't find payment method with Id: "+idPaymentMethod));
        paymentMethod.setName(newName);
        return paymentMethodRepository.save(paymentMethod);
    }

    //Delete Payment Method by Id
    public void deletePaymentMethod(Long idToDelete){
        PaymentMethod paymentMethodToDelete = paymentMethodRepository.findById(idToDelete)
                .orElseThrow(()->new EntityNotFoundException("Cant find payment method with Id: "+idToDelete));
        List<Payment> paymentsWithPaymentMethod = paymentRepository.findByPaymentMethod(paymentMethodToDelete);
        for (Payment payment : paymentsWithPaymentMethod) {
            payment.setPaymentMethod(null);
            paymentRepository.save(payment);
        }
        paymentMethodRepository.delete(paymentMethodToDelete);

    }

    //Get Payment Method By id
    public PaymentMethod getPaymentMethodById(Long idToRemove){
        return paymentMethodRepository.findById(idToRemove)
                .orElseThrow(()->new EntityNotFoundException("Can't find payment method with Id: "+idToRemove));
    }

    //Get All Payment Method
    public List<PaymentMethod> getAllPaymentMethod(){
        return paymentMethodRepository.findAll();
    }






}
