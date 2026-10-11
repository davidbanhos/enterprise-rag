
package com.portfolio.ai.enterpriserag;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vector-documents")
public class VectorDocumentController {

    private final VectorDocumentService service;

    public VectorDocumentController(
            VectorDocumentService service) {
        this.service = service;
    }

    public record IngestRequest(
            @NotBlank String documentId,
            @NotBlank String content
    ) {}

    public record IngestResponse(
            String documentId,
            int chunksStored
    ) {}


    @PostMapping
    public IngestResponse ingest(
            @Valid @RequestBody IngestRequest request) {

        int count = service.ingest(
                request.documentId(),
                request.content()
        );

        return new IngestResponse(
                request.documentId(), count
        );
    }
}
