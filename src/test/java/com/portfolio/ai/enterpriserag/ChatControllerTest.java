package com.portfolio.ai.enterpriserag;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChatControllerTest {

    @Test
    void shouldReturnAiAnswer() {
        ChatService service = mock(ChatService.class);

        when(service.ask("What is RAG?"))
                .thenReturn("RAG combines retrieval and generation.");

        ChatController controller =
                new ChatController(service);

        var request =
                new ChatController.ChatRequest("What is RAG?");

        var response = controller.chat(request);

        assertEquals(
                "RAG combines retrieval and generation.",
                response.answer()
        );

        verify(service).ask("What is RAG?");
    }
}