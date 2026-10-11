package com.portfolio.ai.enterpriserag;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DocumentChunkerTest {

    private final DocumentChunker chunker =
            new DocumentChunker();

    @Test
    void shouldSplitTextIntoChunks() {
        var chunks = chunker.split("ABCDEFGHIJ", 4);

        assertEquals(3, chunks.size());
        assertEquals("ABCD", chunks.get(0));
        assertEquals("EFGH", chunks.get(1));
        assertEquals("IJ", chunks.get(2));
    }

    @Test
    void shouldRejectEmptyContent() {
        assertThrows(
                IllegalArgumentException.class,
                () -> chunker.split(" ", 500)
        );
    }

    @Test
    void shouldRejectInvalidChunkSize() {
        assertThrows(
                IllegalArgumentException.class,
                () -> chunker.split("Hello", 0)
        );
    }
}