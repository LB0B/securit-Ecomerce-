package net.arkx.payementservice.web;

import net.arkx.payementservice.service.ShippingProviderService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShippingProviderController {
    private final ShippingProviderService shippingProviderService;

    public ShippingProviderController(ShippingProviderService shippingProviderService) {
        this.shippingProviderService = shippingProviderService;
    }
}
