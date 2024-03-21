package net.arkx.orderservice.service;

import net.arkx.orderservice.DTOs.OrderDto;
import net.arkx.orderservice.entities.Coupon;
import net.arkx.orderservice.entities.Order;
import net.arkx.orderservice.repository.CouponRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CouponService {
    private final CouponRepository couponRepository;

    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }
    //add coupon
    public void addCoupon(Coupon coupon){
        couponRepository.save(coupon);
    }
    //get all coupons
    public List<Coupon> getAllCoupons(){
        return couponRepository.findAll();
    }
//    get coupon by id
    public Coupon getCouponById(long id) throws Exception {
        Optional<Coupon> optional = couponRepository.findById(id);
        if (optional.isPresent()){
            return optional.get();
        }
        else{
            throw new Exception();
        }
    }
    //delete all coupons
    public void deleteAllCoupons(){
        couponRepository.deleteAll();
    }
    //delete coupon by id
    public void deleteCoupon(long id) throws Exception {
        if (couponRepository.existsById(id)){
            couponRepository.deleteById(id);
        }
        else{
            throw new Exception();
        }
    }
    //update coupon
    public void updateCoupon(long id, Coupon newCoupon) throws Exception {
        Optional<Coupon> optional = couponRepository.findById(id);
        if (optional.isPresent()){
            Coupon coupon = optional.get();
            coupon.setCode(newCoupon.getCode());
            coupon.setDiscount(newCoupon.getDiscount());
            coupon.setExpirationDate(newCoupon.getExpirationDate());
            couponRepository.save(coupon);
        }
        else{
            throw new Exception();
        }
    }
}
