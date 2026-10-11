package com.example.order_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message="email is required")
    @Email(message="valid email required")
    private String email;
    @NotBlank(message = "password required")
    private String password;
}
