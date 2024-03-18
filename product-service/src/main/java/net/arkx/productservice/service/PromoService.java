package net.arkx.productservice.service;

import net.arkx.productservice.repository.PromoRepository;
import org.springframework.stereotype.Service;

@Service
public class PromoService {
    private final PromoRepository promoRepository;

    public PromoService(PromoRepository promoRepository) {
        this.promoRepository = promoRepository;
    }
}
