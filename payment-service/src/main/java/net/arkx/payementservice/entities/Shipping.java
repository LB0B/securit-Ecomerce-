package net.arkx.payementservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import net.arkx.payementservice.model.Order;
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
@Entity
public class Shipping {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   private Long trackingNumber;

    @Transient
    @JsonIgnore
    private Order order;
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "shipping_provider_id")
    @JsonIgnoreProperties({"id"})
    private ShippingProvider shippingProvider;
}
