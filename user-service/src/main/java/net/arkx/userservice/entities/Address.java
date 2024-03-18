package net.arkx.userservice.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor @Builder
@Entity
public class Address {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
  private String street;
  private String city;
  private String postalCode;
}
