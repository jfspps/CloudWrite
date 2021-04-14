package com.example.cloudwrite.api.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * A key result of an research piece
 */
@Data
@JsonTypeName("KeyResult")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@JsonPropertyOrder({"id", "description", "priority"})
public class KeyResultDTO {

    private Long id;

    @Schema(description = "Numerical placement of the key result in the article. Lower values have higher priorities.")
    private Integer priority;

    @Schema(description = "A description of the key result")
    private String description;
}
