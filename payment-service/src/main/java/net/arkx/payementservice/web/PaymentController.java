package net.arkx.payementservice.web;

import net.arkx.payementservice.entities.Payment;
import net.arkx.payementservice.entities.PaymentMethod;
import net.arkx.payementservice.service.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/payments")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
    //Get Payment By Id
    @GetMapping("/{id}")
    public Payment getPaymentById(@PathVariable Long id){
        return paymentService.findPaymentById(id);
    }

    // Add Payment
    @PostMapping("/addPayment")
    public void AddPayment(@RequestBody Payment newPayment){
         paymentService.addPayment(newPayment);
    }
    //Update Amount
    @PutMapping("/updateAmount/{id}")
    public void updateAmount(@PathVariable Long id, @RequestBody Long newAmount){
         paymentService.updateAmountPayment(id,newAmount);
    }
    //Update OrderId
    @PutMapping("/updateOrderId/{id}")
    public void updateOrderId(@PathVariable Long id, @RequestBody Long newOrderId){
        paymentService.updateOrderId(id, newOrderId);
    }

    //Update Payment Method Id
    @PutMapping("/updatePaymentMethod/{id}")
    public void updatePaymentMethodId(@PathVariable Long id, @RequestBody PaymentMethod newPaymentMethod) {
        paymentService.updatePaymentMethodId(id, newPaymentMethod);
    }
    // Delete Payment by Id
    @DeleteMapping("/deletePayment/{id}")
    public void removePaymentById(@PathVariable Long id){
        paymentService.deletePayment(id);
    }
}
