package com.commerce.aiservice.service;

import com.commerce.aiservice.memory.ChatMessage;
import com.commerce.aiservice.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConversationMemoryService {
    private final ChatMessageRepository chatMessageRepository;

    public List<ChatMessage> getMessages(String userId,String conversationId){
        return chatMessageRepository.findByUserIdAndConversationId(userId, conversationId);
    }
    public void addMessage(ChatMessage message){
        chatMessageRepository.save(message);
    }

}
