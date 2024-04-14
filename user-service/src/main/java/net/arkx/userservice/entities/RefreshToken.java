package net.arkx.userservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class RefreshToken {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean expire;
    private String value;
    private Instant creation;
    private Instant expiration;

    @Override
    public String toString() {
        return "RefreshToken{" +
                "id=" + id +
                ", expire=" + expire +
                ", value='" + value + '\'' +
                ", creation=" + creation +
                '}';
    }
}
