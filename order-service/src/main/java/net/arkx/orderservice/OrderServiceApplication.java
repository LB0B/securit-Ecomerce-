package net.arkx.orderservice;

import net.arkx.orderservice.entities.Order;
import net.arkx.orderservice.service.OrderService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(OrderService userService) {
        return args -> {
            List<Order> orderList = List.of(
                    Order.builder()
                            .id(50L)
                            .orderDate(new Date())
                            .status("new")
                            .user_id(12L)
                            .build(),
                    Order.builder()
                            .id(45L)
                            .orderDate(new Date())
                            .status("Canceled")
                            .user_id(10L)
                            .build());

            orderList.forEach(userService::save);
        };

    }
}