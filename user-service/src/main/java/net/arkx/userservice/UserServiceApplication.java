package net.arkx.userservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import net.arkx.userservice.entities.Role;
import net.arkx.userservice.entities.User;
import net.arkx.userservice.repository.UserRepository;
import net.arkx.userservice.service.RoleService;
import net.arkx.userservice.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@SpringBootApplication
@OpenAPIDefinition
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class UserServiceApplication {



    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }


   /* @Bean
    CommandLineRunner commandLineRunner(UserService userService) {
        return args -> {
            List<User> userList = List.of(
                    User.builder()
                            .username("test1")
                            .password("1234")
                            .firstName("Hassan")
                            .lastName("Elhoumi")
                            .email("hassan@gmail.com")
                            .build(),
                    User.builder()
                            .username("test2")
                            .password("1234")
                            .firstName("Mohamed")
                            .lastName("Elhannaoui")
                            .email("mohamed@gmail.com")
                            .build()
            );


            userList.forEach(userService::save);


            List<User> allUsers = userService.getAll();
            allUsers.forEach(System.out::println);
        };
    }*/

}

