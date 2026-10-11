package com.example.userservice.controller;

import com.example.userservice.dto.LoginRequest;
import com.example.userservice.dto.SignupRequest;
import com.example.userservice.dto.SignupResponse;
import com.example.userservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(
            @Valid @RequestBody SignupRequest request) {

        SignupResponse response = userService.signup(request);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(
            @Valid @RequestBody LoginRequest request) {

        String token = userService.login(request);
        return ResponseEntity.ok(token);
    }
    @GetMapping("/email/{email}")
    public ResponseEntity<SignupResponse> getUserByEmail(
            @PathVariable String email) {

        SignupResponse response = userService.getUserByEmail(email);
        return ResponseEntity.ok(response);
    }
}