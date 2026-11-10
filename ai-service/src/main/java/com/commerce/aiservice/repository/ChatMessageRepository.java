package com.commerce.aiservice.repository;

import com.commerce.aiservice.memory.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage,Long> {
     List<ChatMessage> findByUserIdAndConversationId(String userId,String conversationId);
}
