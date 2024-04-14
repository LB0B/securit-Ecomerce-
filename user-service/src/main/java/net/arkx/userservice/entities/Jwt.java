package net.arkx.userservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Jwt {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String value;
    private boolean expire;
    private boolean deactivate;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private RefreshToken refreshToken;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE })
    @JoinColumn(name = "user_id")
    private User user;
    @Override
    public String toString() {
        return "Jwt{" +
                "id=" + id +
                ", value='" + value + '\'' +
                ", expire=" + expire +
                ", deactivate=" + deactivate +
                '}';
    }



}
