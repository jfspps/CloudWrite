package com.example.cloudwrite.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * A collection of concepts as part of a fundamental piece
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConceptDTOList {

    private List<ConceptDTO> conceptDTOs;
}
