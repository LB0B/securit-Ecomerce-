package net.arkx.orderservice.DTOs;

import jakarta.persistence.*;
import lombok.*;
import net.arkx.orderservice.model.User;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class OrderDto {
        private String status;
    }
