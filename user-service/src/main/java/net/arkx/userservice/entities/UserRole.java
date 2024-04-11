package net.arkx.userservice.entities;

import lombok.*;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString

public class UserRole {
    private User user;
    private List<String> roles;
}
