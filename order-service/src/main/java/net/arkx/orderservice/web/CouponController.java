package net.arkx.orderservice.web;

import net.arkx.orderservice.service.CouponService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CouponController {
    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }
}
