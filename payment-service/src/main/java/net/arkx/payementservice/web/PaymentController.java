package net.arkx.payementservice.web;

import net.arkx.payementservice.entities.Payment;
import net.arkx.payementservice.service.PaymentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("/payments")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/id/{id}")
    public Payment getPaymentById(@PathVariable Long id){
        return paymentService.findPaymentById(id);
    }
}
