package net.arkx.userservice.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor @Builder
@Entity
public class Notification {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
  private String content;
  private String status;
}
