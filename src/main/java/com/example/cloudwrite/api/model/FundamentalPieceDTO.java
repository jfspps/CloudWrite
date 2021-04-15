package com.example.cloudwrite.api.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * A fundamental piece
 */
@Data
@JsonPropertyOrder({"id", "title", "keyword", "prerequisites", "conceptDTOs", "summary"})
public class FundamentalPieceDTO {

    private Long id;

    @Schema(description = "The title of the article/piece")
    private String title;

    @Schema(description = "Keyword used to search for articles/pieces on file")
    private String keyword;

    @Schema(description = "A summary of the prerequisite knowledge needed")
    private String prerequisites;

    @Schema(description = "The closing remarks and summary of the piece/article")
    private String summary;

    @Schema(description = "A list of the concepts covered")
    private ConceptDTOList conceptDTOs;
}
