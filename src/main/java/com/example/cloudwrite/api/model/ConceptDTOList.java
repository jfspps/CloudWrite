package com.example.cloudwrite.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * A collection of concepts as part of a fundamental piece
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlType(namespace = "http://cloudwrite.com/conceptList",
        propOrder = {"conceptDTOs"})
@XmlAccessorType(XmlAccessType.FIELD)
public class ConceptDTOList {

    @XmlElementWrapper(name = "conceptDTOList", nillable = true)
    @XmlElement(name = "conceptDTO")
    private List<ConceptDTO> conceptDTOs;
}
