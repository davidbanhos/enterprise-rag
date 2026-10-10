package com.portfolio.ai.enterpriserag;

import com.portfolio.ai.enterpriserag.service.AiModelGateway;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final AiModelGateway gateway;

    public ChatService(AiModelGateway gateway) {
        this.gateway = gateway;
    }

    public String ask(String question) {
        return gateway.generateAnswer(question);
    }
}
