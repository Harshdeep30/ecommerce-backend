package com.commerce.aiservice.memory;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="chat_messages")
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String conversationId;
    private String role;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String content;
    protected ChatMessage() {
    }
    public ChatMessage(String conversationId, String role, String content) {
        this.conversationId = conversationId;
        this.role = role;
        this.content = content;
    }
    public String getRole() {
        return role;
    }

    public String getContent() {
        return content;
    }

}
