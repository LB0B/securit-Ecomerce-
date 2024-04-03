package net.arkx.payementservice.web;

import net.arkx.payementservice.entities.PaymentMethod;
import net.arkx.payementservice.service.PaymentMethodService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @RequestMapping("/paymentMethods")
public class PaymentMethodController {
    private final PaymentMethodService paymentMethodService;

    public PaymentMethodController(PaymentMethodService paymentMethodService) {
        this.paymentMethodService = paymentMethodService;
    }
    @PostMapping("/addNewPaymentMethod")
    public PaymentMethod addNewPaymentMethod(PaymentMethod newPaymentMethod){
        return paymentMethodService.addPaymentMethod(newPaymentMethod);
    }

    //Update Payment Method By Id
    @PutMapping("/updateMethodName/{idToUpdate}")
    public PaymentMethod updateMethodName(@PathVariable Long idToUpdate, String newName){
        return paymentMethodService.updatePaymentMethodName(idToUpdate, newName);
    }

    //Delete Payment Method By Id
    @DeleteMapping("/deletePaymentMethod/{idToDelete}")
    public void removePaymentMethod(@PathVariable Long idToDelete){
        paymentMethodService.deletePaymentMethod(idToDelete);
    }

    //Get Payment Method By id
    @GetMapping("/getPaymentMethod/{idToGet}")
    public PaymentMethod getPaymentMethodById(@PathVariable Long idToGet){
        return paymentMethodService.getPaymentMethodById(idToGet);
    }

    //Get All Payment Method
    @GetMapping
    public List<PaymentMethod> getAllPaymentMethod(){
        return paymentMethodService.getAllPaymentMethod();
    }

}
