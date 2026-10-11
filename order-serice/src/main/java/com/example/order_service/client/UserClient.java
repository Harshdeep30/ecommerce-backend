
        package com.example.order_service.client;

import com.example.order_service.dto.SignupResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class UserClient {

    private final RestClient restClient;

    public UserClient() {
        this.restClient = RestClient.builder()
                .baseUrl("http://user-service:8080")
                .build();
    }

    public SignupResponse getUserByEmail(String email, String token) {
        return restClient.get()
                .uri("/users/email/{email}", email)
                .header("Authorization", token)
                .retrieve()
                .body(SignupResponse.class);
    }
}
