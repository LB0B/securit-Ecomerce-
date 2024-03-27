package net.arkx.payementservice.model;

import lombok.*;

import java.util.Date;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Order {
    private Long id;
    private Long userId;
    private Date orderDate;
    private String status;

}