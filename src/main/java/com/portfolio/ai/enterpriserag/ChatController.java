package com.portfolio.ai.enterpriserag;

import com.portfolio.ai.enterpriserag.dto.ChatReponse;
import com.portfolio.ai.enterpriserag.dto.ChatRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService service;

    public ChatController(ChatService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ChatReponse> chat(
            @Valid @RequestBody ChatRequest request) {
        String answer = service.ask(request.question());

        return ResponseEntity.ok(new ChatReponse(answer));
    }
}
