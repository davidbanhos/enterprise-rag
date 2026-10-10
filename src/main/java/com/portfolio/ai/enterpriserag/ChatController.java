package com.portfolio.ai.enterpriserag;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService service;

    public ChatController(ChatService service) {
        this.service = service;
    }

    public record ChatRequest(
        @NotBlank String question
    ) {}

    public record ChatResponse(String answer) {}

    @PostMapping
    public ChatResponse chat(
            @Valid @RequestBody ChatRequest request) {

        String answer = service.ask(request.question());
        return new ChatResponse(answer);
    }
}
