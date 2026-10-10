package com.portfolio.ai.enterpriserag.service;

import com.portfolio.ai.enterpriserag.ChatService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChatServiceTest {

    @Mock
    private AiModelGateway gateway;

    @InjectMocks
    private ChatService chatService;

    @Test
    void shouldReturnGeneratedAnswer() {
        // Arrange
        when(gateway.generateAnswer("What is RAG?"))
                .thenReturn("Retrieval-Augmented Generation");

        // Act
        String result = chatService.ask("What is RAG?");

        // Assert
        assertEquals("Retrieval-Augmented Generation", result);

        verify(gateway).generateAnswer("What is RAG?");
        verifyNoMoreInteractions(gateway);
    }

    @Test
    void shouldPropagateGatewayFailure() {
        when(gateway.generateAnswer("Hello"))
                .thenThrow(new RuntimeException("Provider unavailable"));

        assertThrows(RuntimeException.class,
                () -> chatService.ask("Hello"));
    }
}