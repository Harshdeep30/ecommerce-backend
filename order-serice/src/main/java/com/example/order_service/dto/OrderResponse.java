package com.example.order_service.dto;

import lombok.Data;

@Data
public class OrderResponse {
    private Long id;
    private Long userId;
    private Long productId;
    private int quantity;
    private double amount;
    private String status;
}
