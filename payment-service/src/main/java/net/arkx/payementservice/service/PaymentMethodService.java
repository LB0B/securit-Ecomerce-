package net.arkx.payementservice.service;

import net.arkx.payementservice.repository.PaymentMethodRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentMethodService {
    private final PaymentMethodRepository paymentMethodRepository;

    public PaymentMethodService(PaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }
}
