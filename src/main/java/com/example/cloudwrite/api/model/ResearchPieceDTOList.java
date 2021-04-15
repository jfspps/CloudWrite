package com.example.cloudwrite.api.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * A collection of research pieces
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResearchPieceDTOList {

    List<ResearchPieceDTO> researchPieceDTOS;
}
