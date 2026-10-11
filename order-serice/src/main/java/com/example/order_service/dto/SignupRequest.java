package com.example.order_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupRequest {
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "email is required")
    @Email(message = "Invalid email")
    private String email;
    @NotBlank(message = "passoword is required")
    @Size(min=8,message = "password must contains atleast 8 characters")
    private String password;
}
