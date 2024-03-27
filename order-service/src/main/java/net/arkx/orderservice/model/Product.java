package net.arkx.orderservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor
//Product model
public class Product {
    private long id;
    private String name;
    private double price;
    //If product has promo
    private boolean promo;
    //The amount of the discount
    private double discount;
}
