package net.arkx.orderservice.entities;

import jakarta.persistence.*;
import lombok.*;
import net.arkx.orderservice.model.Product;
import net.arkx.orderservice.model.User;

import java.util.Date;
import java.util.List;

@Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "Commande")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    //add product attribute
    @Transient
    private List<Product> products;
    private Date orderDate;
    private String status;
    @Transient
    private User user;
    private long userId;
    //add invoice attribute
    @OneToOne
    private Invoice invoice;

}
