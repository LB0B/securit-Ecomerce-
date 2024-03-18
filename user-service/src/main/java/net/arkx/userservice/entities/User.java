package net.arkx.userservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "Utilisateur")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Address> addresses = new ArrayList<>();
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Notification notification;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private WishList wishlist;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Review review;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
  /*  private Date createdAt;
    private Date updateAt;
    private Date lastLogin;*/

}
