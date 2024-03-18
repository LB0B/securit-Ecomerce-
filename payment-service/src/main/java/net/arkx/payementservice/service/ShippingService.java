package net.arkx.payementservice.service;

import net.arkx.payementservice.repository.ShippingRepository;
import org.springframework.stereotype.Service;

@Service
public class ShippingService {
    private final ShippingRepository shippingRepository;

    public ShippingService(ShippingRepository shippingRepository) {
        this.shippingRepository = shippingRepository;
    }
}
