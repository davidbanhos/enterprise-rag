package com.portfolio.ai.enterpriserag;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DocumentChunker {

    public List<String> split(String content, int chunkSize) {

        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException(
                     "Document content cannot be null or blank");
        }


        if (chunkSize <= 0) {
            throw new IllegalArgumentException(
                    "Chunk size must be positive");
        }

        List<String> chunks = new ArrayList<>();

        for (int start = 0; start < content.length();
             start += chunkSize) {

            int end = Math.min(
                    start + chunkSize, content.length());

            String chunk = content.substring(start, end);

            if (!chunk.isBlank()) {
                chunks.add(chunk);
            }
        }

        return chunks;
    }




}
