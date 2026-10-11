package com.example.order_service.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;
    private Long userId;
   private Long productId;
  private  int quantity;
   private double amount;
    private String status;
}
