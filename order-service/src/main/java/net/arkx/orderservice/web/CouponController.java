package net.arkx.orderservice.web;

import net.arkx.orderservice.DTOs.OrderDto;
import net.arkx.orderservice.entities.Coupon;
import net.arkx.orderservice.entities.Order;
import net.arkx.orderservice.service.CouponService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("coupons")
public class CouponController {
    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }
    @GetMapping
    public List<Coupon> getCoupons(){
        return couponService.getAllCoupons();
    }
    @GetMapping("{id}")
    public Coupon getCouponById(@PathVariable long id) throws Exception {
        return couponService.getCouponById(id);
    }
    @PostMapping
    public void addCoupon(@RequestBody Coupon coupon){
        couponService.addCoupon(coupon);
    }
    @DeleteMapping
    public void deleteAllCoupons(){
        couponService.deleteAllCoupons();
    }
    @DeleteMapping("{id}")
    public void deleteCouponById(@PathVariable long id) throws Exception {
        couponService.deleteCoupon(id);
    }
    @PutMapping("{id}")
    public void updateCoupon(@PathVariable long id, @RequestBody Coupon coupon) throws Exception {
        couponService.updateCoupon(id, coupon);
    }
    @GetMapping("sort")
    public List<Coupon> sort(@RequestParam(value = "desc" , defaultValue = "false" ) String desc){
        return couponService.sortCouponByDiscount(desc.equals("true"));
    }
    @GetMapping("filter")
    public List<Coupon> filterByPrice(@RequestParam(value = "min") double min,
                                    @RequestParam(value = "max") double max){
        return couponService.filterByDiscount(min, max);
    }
}
