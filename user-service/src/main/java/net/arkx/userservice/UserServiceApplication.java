package net.arkx.userservice;

import net.arkx.userservice.entities.User;
import net.arkx.userservice.repository.UserRepository;
import net.arkx.userservice.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }


    @Bean
    CommandLineRunner commandLineRunner(UserService userService) {
        return args -> {
            List<User> userList = List.of(
                    User.builder()
                            .userName("test1")
                            .password("1234")
                            .firstName("Hassan")
                            .lastName("Elhoumi")
                            .email("hassan@gmail.com")
                            .build(),
                    User.builder()
                            .userName("test2")
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
    }

}

