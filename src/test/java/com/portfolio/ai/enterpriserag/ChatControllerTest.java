package com.portfolio.ai.enterpriserag;

import com.portfolio.ai.enterpriserag.dto.ChatReponse;
import com.portfolio.ai.enterpriserag.dto.ChatRequest;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.model.ChatResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChatControllerTest {

    @Test
    void shouldReturnAiAnswer() {
        ChatService service = mock(ChatService.class);


        when(service.ask("What is RAG?"))
                .thenReturn("Retrieval-Augmented Generation");

        ChatController controller =
                new ChatController(service);

        var response = controller.chat(
                new ChatRequest("What is RAG?"));

        assertEquals(200, response.getStatusCode().value());

        ChatReponse body = response.getBody();
        assertNotNull(body);

        assertEquals(
                "Retrieval-Augmented Generation",
                body.answer());

        verify(service).ask("What is RAG?");

    }
}