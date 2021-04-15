package com.example.cloudwrite.api.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * A concept as part of a fundamental piece
 */
@Data
@JsonPropertyOrder({"id", "purpose", "description", "priority"})
public class ConceptDTO {

    private Long id;

    @Schema(description = "Purpose of the concept")
    private String purpose;

    @Schema(description = "Description of the concept")
    private String description;

    @Schema(description = "Numerical placement of the concept in the article. Lower values have higher priorities.")
    private Integer priority;
}
