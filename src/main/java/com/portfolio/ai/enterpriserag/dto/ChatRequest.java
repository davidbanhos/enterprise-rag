package com.portfolio.ai.enterpriserag.dto;

import jakarta.validation.constraints.NotBlank;

public record ChatRequest(
        @NotBlank(message = "Question must not be blank")
        String question
) {}
