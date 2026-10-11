    package com.example.order_service.dto;

    import jakarta.validation.constraints.NotNull;
    import jakarta.validation.constraints.Positive;
    import lombok.Data;

    @Data
    public class CreateOrderRequest {

        @NotNull(message = "Product ID is required")
        private Long productId;

        @Positive(message = "Quantity must be greater than 0")
        private int quantity;

    }
