package com.example.productservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class CreateProductRequest {
    @NotBlank(message = "Product name is required")
    private String name;
    @NotBlank(message = "Please provide the descriotion for the product")
    private String description;
    @Positive(message = "price cant be negative")
    private double price;
    @PositiveOrZero(message = "stock cant be negative")
    private int stock;
}
