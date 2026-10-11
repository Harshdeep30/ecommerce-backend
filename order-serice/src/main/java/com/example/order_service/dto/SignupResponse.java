package com.example.order_service.dto;

import lombok.Data;

@Data
public class SignupResponse {
    private Long id;
    private String name;
    private String email;
}
