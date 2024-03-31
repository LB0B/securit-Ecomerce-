package net.arkx.payementservice.web;

import net.arkx.payementservice.entities.Shipping;
import net.arkx.payementservice.service.ShippingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("/shippings")
public class ShippingController {
    private final ShippingService shippingService;

    public ShippingController(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    @GetMapping("/{id}")
    public Shipping getShippingById(@PathVariable Long id){
        return shippingService.findShippingById(id);
    }


}
