package com.example.productservice.dto;

import lombok.Data;

@Data
public class ProductSearchRequest {
    private String keyword;
    private Double minPrice;
    private Double maxPrice;
}
