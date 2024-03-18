package net.arkx.orderservice.entities;

import jakarta.persistence.*;
import lombok.*;
import net.arkx.orderservice.model.User;

import java.util.Date;

@Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "Commande")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date orderDate;
    private String status;
    @Transient
    private User user;
    private Long user_id;

}
