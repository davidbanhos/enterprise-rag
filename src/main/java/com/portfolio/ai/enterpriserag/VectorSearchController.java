
package com.portfolio.ai.enterpriserag;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/search")
public class VectorSearchController {

    private final VectorDocumentService service;

    public VectorSearchController(
            VectorDocumentService service) {
        this.service = service;
    }

    public record SearchRequestDto(
            @NotBlank String question,
            @Min(1) @Max(20) int topK
    ) {}

    public record SearchResultDto(
            String text,
            Map<String, Object> metadata,
            Double score
    ) {}

    @PostMapping
    public List<SearchResultDto> search(
            @Valid @RequestBody SearchRequestDto request) {

        return service.search(
                        request.question(),
                        request.topK()
                )
                .stream()
                .map(doc -> new SearchResultDto(
                        doc.getText(),
                        doc.getMetadata(),
                        doc.getScore()
                ))
                .toList();
    }
}
