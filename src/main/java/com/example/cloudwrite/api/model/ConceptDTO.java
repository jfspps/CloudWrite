package com.example.cloudwrite.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.xml.bind.annotation.*;

/**
 * A concept as part of a fundamental piece
 */
@Data
@XmlType(namespace = "http://cloudwrite.com/concept",
        propOrder = {"id", "purpose", "description", "priority"})
@XmlAccessorType(XmlAccessType.FIELD)
public class ConceptDTO {

    @XmlTransient
    private Long id;

    @XmlElement(defaultValue = "")
    @Schema(description = "Purpose of the concept")
    private String purpose;

    @XmlElement(defaultValue = "")
    @Schema(description = "Description of the concept")
    private String description;

    @XmlElement(defaultValue = "0")
    @Schema(description = "Numerical placement of the concept in the article. Lower values have higher priorities.")
    private Integer priority;
}
