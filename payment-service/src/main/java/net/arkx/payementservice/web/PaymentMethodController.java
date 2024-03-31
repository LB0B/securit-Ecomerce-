package net.arkx.payementservice.web;

import net.arkx.payementservice.entities.PaymentMethod;
import net.arkx.payementservice.service.PaymentMethodService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("/paymentMethods")
public class PaymentMethodController {
    private final PaymentMethodService paymentMethodService;

    public PaymentMethodController(PaymentMethodService paymentMethodService) {
        this.paymentMethodService = paymentMethodService;
    }
    @PostMapping()
    public PaymentMethod addNewPaymentMethod(PaymentMethod newPaymentMethod){
        return paymentMethodService.addPaymentMethod(newPaymentMethod);
    }

}
