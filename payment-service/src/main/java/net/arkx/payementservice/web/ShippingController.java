package net.arkx.payementservice.web;

import net.arkx.payementservice.service.ShippingService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShippingController {
    private final ShippingService shippingService;

    public ShippingController(ShippingService shippingService) {
        this.shippingService = shippingService;
    }
}
