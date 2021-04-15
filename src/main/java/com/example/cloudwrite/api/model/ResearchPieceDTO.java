package com.example.cloudwrite.api.model;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * A research piece/article
 */
@Data
@JsonPropertyOrder({
        "id", "title", "keyword", "standfirstDTO", "researchPurpose", "currentProgress", "keyResultDTOs", "futureWork", "citations"})
public class ResearchPieceDTO {

    private Long id;

    @Schema(description = "The title of the article/piece")
    private String title;

    @Schema(description = "Keyword used to search for articles/pieces on file")
    private String keyword;

    @Schema(description = "The standfirst or headline of the article/piece")
    private StandfirstDTO standfirstDTO;

    @Schema(description = "A summary of the top-level areas/issues that the research attempts to address")
    private String researchPurpose;

    @Schema(description = "A summary of the current research efforts conducted")
    private String currentProgress;

    @Schema(description = "A list of key results")
    private KeyResultDTOList keyResultDTOs;

    @Schema(description = "A summary of future work which follows the research presented")
    private String futureWork;

    @Schema(description = "A list of references/citations relevant to the article/piece")
    private CitationDTOList citationDTOs;
}
