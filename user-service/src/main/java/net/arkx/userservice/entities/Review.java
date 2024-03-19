package net.arkx.userservice.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor @Builder
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String content ;
    private int rating ;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
