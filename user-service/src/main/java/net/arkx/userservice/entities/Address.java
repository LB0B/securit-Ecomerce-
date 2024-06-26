package net.arkx.userservice.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor @Builder
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String street ;
    private String city ;
    private Long postaleCode ;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
