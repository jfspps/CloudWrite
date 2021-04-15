package com.example.cloudwrite.api.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * A citation as part of an research piece
 */
@Data
@JsonPropertyOrder({"id", "ref"})
public class CitationDTO {

    private Long id;

    @Schema(description = "Citation used as part of the research article/piece")
    private String ref;
}

// https://stackoverflow.com/questions/36746223/spring-boot-json-root-name for custom root names