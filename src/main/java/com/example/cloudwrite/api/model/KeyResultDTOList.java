package com.example.cloudwrite.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * A collection of key results as part of an exposition piece
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlType(namespace = "http://cloudwrite.com/keyResultList",
        propOrder = {"keyResultDTOs"})
@XmlAccessorType(XmlAccessType.FIELD)
public class KeyResultDTOList {

    @XmlElementWrapper(name = "keyResultDTOList", nillable = true)
    @XmlElement(name = "keyResultDTO")
    private List<KeyResultDTO> keyResultDTOs;
}
