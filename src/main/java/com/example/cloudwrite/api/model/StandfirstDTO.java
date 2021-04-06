package com.example.cloudwrite.api.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A standfirst (headline) of an exposition piece
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeName("Standfirst")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@JsonPropertyOrder({"id", "rationale", "description"})
public class StandfirstDTO {

    private Long id;

    @Schema(description = "An introduction to the top-level relevance and need of research")
    private String rationale;

    @Schema(description = "An outline of research subtopics and methodologies applied")
    private String approach;
}
