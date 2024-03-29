package net.arkx.orderservice;

import net.arkx.orderservice.entities.Order;
import net.arkx.orderservice.repository.OrderRepository;
import net.arkx.orderservice.service.OrderService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
@EnableFeignClients
@SpringBootApplication
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(OrderRepository orderRepository) {
        return args -> {
            List<Order> orderList = List.of(
                    Order.builder()
                            .id(50L)
                            .orderDate(new Date())
                            .status("new")
                            .userId(1L)
                            .build(),
                    Order.builder()
                            .id(45L)
                            .orderDate(new Date())
                            .status("Canceled")
                            .userId(2L)
                            .build());

            orderList.forEach(orderRepository::save);
        };

    }
}