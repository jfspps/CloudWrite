package com.example.cloudwrite.api.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * A collection of exposition pieces
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlType(namespace = "http://cloudwrite.com/expoPieceList",
        propOrder = {"expositionPieceDTOs"})
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class ExpositionPieceDTOList {

    @XmlElementWrapper(name = "expositionPieceDTOList", nillable = true)
    @XmlElement(name = "expositionPieceDTO")
    List<ExpositionPieceDTO> expositionPieceDTOs;
}
