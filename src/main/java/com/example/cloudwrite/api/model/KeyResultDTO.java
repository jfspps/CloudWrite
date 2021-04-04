package com.example.cloudwrite.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class KeyResultDTO {

    private Long id;

    @Schema(description = "A description of the key result")
    private String description;

    @Schema(description = "Numerical placement of the key result in the article. Lower values have higher priorities.")
    private Integer priority;
}
