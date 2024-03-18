package net.arkx.payementservice.service;

import net.arkx.payementservice.repository.ShippingProviderRepository;
import org.springframework.stereotype.Service;

@Service
public class ShippingProviderService {
    private final ShippingProviderRepository shippingProviderRepository;

    public ShippingProviderService(ShippingProviderRepository shippingProviderRepository) {
        this.shippingProviderRepository = shippingProviderRepository;
    }
}
