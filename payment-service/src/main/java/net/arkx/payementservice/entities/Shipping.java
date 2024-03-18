package net.arkx.payementservice.entities;

import jakarta.persistence.*;

@Entity
public class Shipping {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int trackingNumber;
    @ManyToOne
    @JoinColumn(name = "shipping_provider_id")
    private ShippingProvider shippingProvider;
}
