package com.commerce.aiservice.dto;

import lombok.Data;

@Data
public class ChatResponse {
    private String reply;
    private String conversationId;
    public ChatResponse(String reply, String conversationId) {
        this.reply = reply;
        this.conversationId = conversationId;
    }
}
