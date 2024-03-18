package net.arkx.orderservice.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class User {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
}
