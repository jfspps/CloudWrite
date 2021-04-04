package com.example.cloudwrite.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CitationDTO {

    private Long id;

    @Schema(description = "Citation used as part of the exposition article/piece")
    private String ref;
}
