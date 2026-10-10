package com.portfolio.ai.enterpriserag;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final ChatClient chatClient;

    public ChatService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public String ask(String question) {
        return chatClient.prompt()
                .system("""
                    You are an enterprise AI assistant.
                    Answer clearly and concisely.
                    If uncertain, acknowledge uncertainty.
                    """)
                .user(question)
                .call()
                .content();
    }
}
