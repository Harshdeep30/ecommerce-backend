package com.example.userservice.dto;

import lombok.Data;

@Data
public class SignupResponse {
    private Long id;
    private String name;
    private String email;
}
