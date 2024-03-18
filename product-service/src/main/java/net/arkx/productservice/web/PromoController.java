package net.arkx.productservice.web;

import net.arkx.productservice.service.PromoService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PromoController {
    private final PromoService promoService;

    public PromoController(PromoService promoService) {
        this.promoService = promoService;
    }
}
