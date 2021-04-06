package com.example.cloudwrite.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * A collection of fundamental pieces
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlType(namespace = "http://cloudwrite.com/fundamentalPieceList",
        propOrder = {"fundamentalPieceDTOs"})
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class FundamentalPieceDTOList {

    @XmlElementWrapper(name = "fundamentalPieceDTOList", nillable = true)
    @XmlElement(name = "fundamentalPieceDTO")
    List<FundamentalPieceDTO> fundamentalPieceDTOs;
}
