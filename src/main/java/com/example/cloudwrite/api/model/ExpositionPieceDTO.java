package com.example.cloudwrite.api.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * An exposition of a research
 */
@Data
@JsonTypeName("ExpositionPiece")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@JsonPropertyOrder({
        "id", "title", "keyword", "standfirstDTO", "expositionPurpose", "currentProgress", "keyResultDTOs", "futureWork", "citations"})
public class ExpositionPieceDTO {

    private Long id;

    @Schema(description = "The title of the article/piece")
    private String title;

    @Schema(description = "Keyword used to search for articles/pieces on file")
    private String keyword;

    @JsonProperty("standfirst")
    @Schema(description = "The standfirst or headline of the article/piece")
    private StandfirstDTO standfirstDTO;

    @Schema(description = "A summary of the top-level areas/issues that the research attempts to address")
    private String expositionPurpose;

    @Schema(description = "A summary of the current research efforts conducted")
    private String currentProgress;

    @JsonProperty("keyResults")
    @Schema(description = "A list of key results")
    private KeyResultDTOList keyResultDTOs;

    @Schema(description = "A summary of future work which follows the research presented")
    private String futureWork;

    @JsonProperty("citations")
    @Schema(description = "A list of references/citations relevant to the article/piece")
    private CitationDTOList citationDTOs;
}
