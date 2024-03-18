package net.arkx.payementservice.web;

import net.arkx.payementservice.service.PaymentMethodService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentMethodController {
    private final PaymentMethodService paymentMethodService;

    public PaymentMethodController(PaymentMethodService paymentMethodService) {
        this.paymentMethodService = paymentMethodService;
    }
}
