package com.example.cloudwrite.api.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A standfirst (headline) of an research piece
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"id", "rationale", "approach"})
public class StandfirstDTO {

    private Long id;

    @Schema(description = "An introduction to the top-level relevance and need of research")
    private String rationale;

    @Schema(description = "An outline of research subtopics and methodologies applied")
    private String approach;
}
