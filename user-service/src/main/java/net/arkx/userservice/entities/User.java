package net.arkx.userservice.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "Utilisateur")
public class User {


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String userName ;
    private String email;
    private String password;
    private String firstName ;
    private String lastName ;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Review> reviews;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Address> addresses;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Notification> notifications;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))

    private Set<Role> roles = new HashSet<>();
  /*  private Date createdAt;
    private Date updateAt;
    private Date lastLogin;*/

}
