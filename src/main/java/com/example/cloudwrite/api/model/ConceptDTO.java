package com.example.cloudwrite.api.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ConceptDTO {

    private Long id;

    private String purpose;

    private String description;

    private Integer priority;
}
