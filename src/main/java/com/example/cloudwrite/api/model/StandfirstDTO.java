package com.example.cloudwrite.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StandfirstDTO {

    private Long id;

    @Schema(description = "An introduction to the top-level relevance and need of research")
    private String rationale;

    @Schema(description = "An outline of research subtopics and methodologies applied")
    private String approach;
}
