package net.arkx.orderservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor
//Product model
public class Product {
    private Long id;
    private String name;
    private Double price;
}
