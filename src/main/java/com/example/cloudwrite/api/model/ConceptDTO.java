package com.example.cloudwrite.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ConceptDTO {

    private Long id;

    @Schema(description = "Purpose of the concept")
    private String purpose;

    @Schema(description = "Description of the concept")
    private String description;

    @Schema(description = "Numerical placement of the concept in the article. Lower values have higher priorities.")
    private Integer priority;
}
