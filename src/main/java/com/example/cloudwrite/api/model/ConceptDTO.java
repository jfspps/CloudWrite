package com.example.cloudwrite.api.model;

import lombok.Data;

@Data
public class ConceptDTO {

    private Long id;

    private String purpose;

    private String description;

    private Integer priority;
}
