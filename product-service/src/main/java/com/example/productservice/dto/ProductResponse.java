package com.example.productservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private double price;
    private int stock;


}
