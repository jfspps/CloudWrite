package com.example.cloudwrite.api.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * A concept as part of a fundamental piece
 */
@Data
@JsonTypeName("Concept")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
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
