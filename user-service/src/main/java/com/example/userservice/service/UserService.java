package com.example.userservice.service;

import com.example.userservice.Security.JwtService;
import com.example.userservice.dto.LoginRequest;
import com.example.userservice.dto.SignupRequest;
import com.example.userservice.dto.SignupResponse;
import com.example.userservice.entity.User;
import com.example.userservice.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public UserService(JwtService jwtService, UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.jwtService=jwtService;
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
    }
    public SignupResponse signup(SignupRequest request){
        User user=new User();
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("User with this email already exists");

        }
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER");
        userRepository.save(user);
        SignupResponse response=new SignupResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        return response;
    }

    public String login(LoginRequest request){
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword() )){
            throw new RuntimeException("Invalid password");
        }
       return jwtService.generateToken(user.getEmail());


    }
    public SignupResponse getUserByEmail(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        SignupResponse response = new SignupResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());

        return response;
    }
}
