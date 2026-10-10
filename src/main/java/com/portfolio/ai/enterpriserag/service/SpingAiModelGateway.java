package com.portfolio.ai.enterpriserag.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

@Component
public class SpingAiModelGateway implements AiModelGateway {

    private final ChatClient chatClient;

    public SpingAiModelGateway(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @Override
    public String generateAnswer(String question) {
        return chatClient.prompt()
                .user(question)
                .call()
                .content();
    }
}
