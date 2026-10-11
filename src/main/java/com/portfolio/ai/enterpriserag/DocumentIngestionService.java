package com.portfolio.ai.enterpriserag;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class DocumentIngestionService {

    private final EmbeddingModel embeddingModel;
    private final DocumentChunker chunker;


    public DocumentIngestionService(
            EmbeddingModel embeddingModel,
            DocumentChunker chunker) {
        this.embeddingModel = embeddingModel;
        this.chunker = chunker;
    }

    public List<EmbeddedChunk> ingest(
            String documentId,
            String content) {

        if (documentId == null || documentId.isBlank()) {
            throw new IllegalArgumentException(
                    "Document ID cannot be null or blank");
        }

       List<String> chunks = chunker.split(content, 500);
       List<EmbeddedChunk> results = new ArrayList<>();


       for(int i = 0; i < chunks.size(); i++) {
           String text = chunks.get(i);
           float[] vector = embeddingModel.embed(text);

           results.add(new EmbeddedChunk(documentId, i, text, vector));
       }

        return results;
    }

    public List<EmbeddedChunk> ingestSample()
        throws IOException {

        var resource = new ClassPathResource("documents/remote-work-policy.txt");

        try (var stream = resource.getInputStream()) {
            String content = new String(
                    stream.readAllBytes(),
                    StandardCharsets.UTF_8);
            return ingest("remote-work-policy", content);
        }
    }

}
