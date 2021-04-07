package com.example.cloudwrite.api.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * A collection of exposition pieces
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpositionPieceDTOList {

    @JsonProperty("expositionPieces")
    List<ExpositionPieceDTO> expositionPieceDTOs;
}
