package com.portfolio.ai.enterpriserag;

public record EmbeddedChunk(
        String documentId,
        int chunkIndex,
        String text,
        float[] vector
) {}
