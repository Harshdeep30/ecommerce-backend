package com.example.order_service.event;

import lombok.Data;

@Data
public class OrderCreatedEvent {

    private Long orderId;
    private Long userId;
    private Long productId;
    private int quantity;
    private double amount;
    private String status;
}