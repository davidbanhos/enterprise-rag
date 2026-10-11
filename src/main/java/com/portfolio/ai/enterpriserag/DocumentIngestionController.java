package com.portfolio.ai.enterpriserag;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/documents")
public class DocumentIngestionController {

    private final DocumentIngestionService service;

    public DocumentIngestionController(DocumentIngestionService service) {
        this.service = service;
    }

    public record IngestionResponse(
            String documentId,
            int chunkCount,
            int vectorDimension
    ) {}

    @PostMapping("/ingest-sample")
    public IngestionResponse ingestSample()
        throws IOException {

        var chunks = service.ingestSample();

        return new IngestionResponse(
                "remote-work-policy",
                chunks.size(),
                chunks.isEmpty() ? 0 : chunks.get(0).vector().length
        );
    }

}
