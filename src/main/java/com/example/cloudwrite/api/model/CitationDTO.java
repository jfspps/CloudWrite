package com.example.cloudwrite.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.xml.bind.annotation.*;

/**
 * A citation as part of an exposition piece
 */
@XmlType(namespace = "http://cloudwrite.com/citation",
        propOrder = {"id", "ref"})
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class CitationDTO {

    @XmlTransient
    private Long id;

    @XmlElement(defaultValue = "")
    @Schema(description = "Citation used as part of the exposition article/piece")
    private String ref;
}
