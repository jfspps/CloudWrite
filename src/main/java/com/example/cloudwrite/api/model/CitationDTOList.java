package com.example.cloudwrite.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * A collection of citations as part of an exposition piece
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlType(namespace = "http://cloudwrite.com/citationList",
        propOrder = {"citationDTOs"})
@XmlAccessorType(XmlAccessType.FIELD)
public class CitationDTOList {

    @XmlElementWrapper(name = "citationDTOList", nillable = true)
    @XmlElement(name = "citationDTO")
    private List<CitationDTO> citationDTOs;
}
