package net.arkx.orderservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;
@Getter @Setter
@ToString @NoArgsConstructor @AllArgsConstructor
@Entity
public class Coupon {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String code;
    private double discount;
    private Date expirationDate;
}
