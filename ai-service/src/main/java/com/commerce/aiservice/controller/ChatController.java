package com.commerce.aiservice.controller;

import com.commerce.aiservice.dto.ChatRequest;
import com.commerce.aiservice.dto.ChatResponse;
import com.commerce.aiservice.service.ChatService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
@RestController
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;
    @PostMapping("/ai/chat")
    public ChatResponse chat(@RequestBody ChatRequest request,Authentication authentication){
        String userEmail=authentication.getName();
        return chatService.chat(request,userEmail);

    }
}
