package com.portfolio.ai.enterpriserag;

import org.junit.jupiter.api.Test;
import org.springframework.ai.embedding.EmbeddingModel;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DocumentIngestionServiceTest {

    @Test
    void shouldGenerateEmbeddingForEachChunk() {
        EmbeddingModel model = mock(EmbeddingModel.class);
        DocumentChunker chunker = new DocumentChunker();

        when(model.embed(anyString()))
                .thenReturn(new float[] {0.1f, 0.2f, 0.3f});

        var service = new DocumentIngestionService(
                model, chunker);

        var results = service.ingest(
                "test-policy", "ABCDEFGHIJ");

        assertEquals(1, results.size());
        assertEquals("test-policy", results.get(0).documentId());
        assertEquals(0, results.get(0).chunkIndex());
        assertEquals("ABCDEFGHIJ", results.get(0).text());
        assertEquals(3, results.get(0).vector().length);

        verify(model, times(1)).embed("ABCDEFGHIJ");
    }

    @Test
    void shouldRejectMissingDocumentId() {
        var service = new DocumentIngestionService(
                mock(EmbeddingModel.class),
                new DocumentChunker());

        assertThrows(
                IllegalArgumentException.class,
                () -> service.ingest("", "Some content")
        );
    }
}