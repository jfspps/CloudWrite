package com.example.cloudwrite.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.xml.bind.annotation.*;

/**
 * A key result of an exposition piece
 */
@Data
@XmlType(namespace = "http://cloudwrite.com/keyResult",
        propOrder = {"id", "description", "priority"})
@XmlAccessorType(XmlAccessType.FIELD)
public class KeyResultDTO {

    @XmlTransient
    private Long id;

    @XmlElement(defaultValue = "")
    @Schema(description = "A description of the key result")
    private String description;

    @XmlElement(defaultValue = "0")
    @Schema(description = "Numerical placement of the key result in the article. Lower values have higher priorities.")
    private Integer priority;
}
