package net.arkx.userservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
@Entity
public class Validation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Instant creation;
    private Instant activation;
    private Instant expiration;
    private String code;
    @OneToOne(cascade = CascadeType.ALL)
    private User user;
}
