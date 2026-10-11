
package com.portfolio.ai.enterpriserag;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class VectorDocumentService {

    private final VectorStore vectorStore;
    private final DocumentChunker chunker;

    public VectorDocumentService(
            VectorStore vectorStore,
            DocumentChunker chunker) {
        this.vectorStore = vectorStore;
        this.chunker = chunker;
    }

    public int ingest(String documentId, String content) {
        if (documentId == null || documentId.isBlank()) {
            throw new IllegalArgumentException(
                    "Document ID must not be blank");
        }

        List<String> chunks = chunker.split(content, 500);
        List<Document> documents = new ArrayList<>();

        for (int i = 0; i < chunks.size(); i++) {
            String chunkId = UUID.nameUUIDFromBytes(
                    (documentId + ":" + i)
                            .getBytes(StandardCharsets.UTF_8)
            ).toString();

            Document document = new Document(
                    chunkId,
                    chunks.get(i),
                    Map.of(
                            "documentId", documentId,
                            "chunkIndex", i,
                            "source", "enterprise-document"
                    )
            );

            documents.add(document);
        }

        vectorStore.add(documents);
        return documents.size();
    }

    public List<Document> search(String question, int topK) {
        if (question == null || question.isBlank()) {
            throw new IllegalArgumentException(
                    "Search question must not be blank");
        }

        if (topK < 1 || topK > 20) {
            throw new IllegalArgumentException(
                    "topK must be between 1 and 20");
        }

        SearchRequest request = SearchRequest.builder()
                .query(question)
                .topK(topK)
                .similarityThreshold(0.0)
                .build();

        return vectorStore.similaritySearch(request);
    }
}
