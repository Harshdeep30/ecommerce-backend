package com.example.productservice.dto;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UpdateStockRequest {

    @Positive(message = "quantity must be greater than 0")
    private int quantity;
}