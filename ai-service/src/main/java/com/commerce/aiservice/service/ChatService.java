package com.commerce.aiservice.service;

import com.commerce.aiservice.dto.ChatRequest;
import com.commerce.aiservice.dto.ChatResponse;
import com.commerce.aiservice.memory.ChatMessage;
import com.commerce.aiservice.tool.ProductSearchTool;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.messages.AssistantMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatClient chatClient;
    private final ConversationMemoryService conversationMemoryService;
    private final ProductSearchTool productSearchTool;

    public ChatResponse chat(ChatRequest request,String userId) {
        String conversationId = request.getConversationId();
        if (conversationId == null || conversationId.isBlank()) {
            conversationId = UUID.randomUUID().toString();
        }
        List<ChatMessage> history = conversationMemoryService.getMessages(userId,conversationId);

        List<Message> messages = new ArrayList<>();
        for (ChatMessage msg : history) {

            if (msg.getRole().equals("USER")) {
                messages.add(new UserMessage(msg.getContent()));
            } else if (msg.getRole().equals("ASSISTANT")) {
                messages.add(new AssistantMessage(msg.getContent()));
            }
        }
        conversationMemoryService.addMessage(
                new ChatMessage(userId,conversationId,"USER", request.getMessage())
        );

        messages.add(new UserMessage(request.getMessage()));
        String ans = chatClient.prompt().messages(messages).tools(productSearchTool).call().content();
        conversationMemoryService.addMessage(
                new ChatMessage(userId,conversationId,"ASSISTANT", ans)
        );
        return new ChatResponse(ans,conversationId);
    }

}
