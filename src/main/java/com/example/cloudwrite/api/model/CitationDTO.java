package com.example.cloudwrite.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * A citation as part of an exposition piece
 */
@Data
@JsonTypeName("Citation")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@JsonPropertyOrder({"id", "ref"})
public class CitationDTO {

    private Long id;

    @JsonProperty("references")
    @Schema(description = "Citation used as part of the exposition article/piece")
    private String ref;
}

// https://stackoverflow.com/questions/36746223/spring-boot-json-root-name for custom root names