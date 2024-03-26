package net.arkx.orderservice.repository;

import net.arkx.orderservice.entities.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon,Long> {
    //find the coupon by code
    Optional<Coupon> findByCode(String code);
    List<Coupon> findByDiscountBetween(double min, double max);
    List<Coupon> findAllByOrderByDiscountAsc();
    List<Coupon> findAllByOrderByDiscountDesc();

}
