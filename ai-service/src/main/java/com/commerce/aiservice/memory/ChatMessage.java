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
    private String userId;
    protected ChatMessage() {
    }
    public ChatMessage(String userId,String conversationId, String role, String content) {
        this.conversationId = conversationId;
        this.userId=userId;
        this.role = role;
        this.content = content;
    }
    public String getUserId() {
        return userId;
    }

    public String getConversationId() {
        return conversationId;
    }
    public String getRole() {
        return role;
    }

    public String getContent() {
        return content;
    }

}
