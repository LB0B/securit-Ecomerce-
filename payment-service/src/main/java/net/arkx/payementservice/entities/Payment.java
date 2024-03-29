package net.arkx.payementservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import net.arkx.payementservice.model.Order;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor @ToString
@Entity
public class Payment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long amount;
    @ManyToOne
    @JoinColumn(name = "payment_method_id")
    @JsonIgnoreProperties({"id"})
    private PaymentMethod paymentMethod;
    @Transient
    private Order order;
    private Long orderId;



}
