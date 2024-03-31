package net.arkx.payementservice.entities;

import jakarta.persistence.*;
import lombok.*;
import net.arkx.payementservice.model.Order;
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
@Entity
public class Shipping {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int trackingNumber;
    @Transient
    private Order order;
    private Long orderId;


    @ManyToOne
    @JoinColumn(name = "shipping_provider_id")
    private ShippingProvider shippingProvider;
}
