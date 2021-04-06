package com.example.cloudwrite.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;

/**
 * A standfirst (headline) of an exposition piece
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlType(namespace = "http://cloudwrite.com/standfirst",
        propOrder = {"id", "rationale", "approach"})
@XmlAccessorType(XmlAccessType.FIELD)
public class StandfirstDTO {

    @XmlTransient
    private Long id;

    @XmlElement(defaultValue = "")
    @Schema(description = "An introduction to the top-level relevance and need of research")
    private String rationale;

    @XmlElement(defaultValue = "")
    @Schema(description = "An outline of research subtopics and methodologies applied")
    private String approach;
}
