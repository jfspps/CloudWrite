package com.example.cloudwrite.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * A collection of key results as part of an research piece
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeyResultDTOList {

    private List<KeyResultDTO> keyResultDTOs;
}
