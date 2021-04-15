package com.example.cloudwrite.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * A collection of fundamental pieces
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FundamentalPieceDTOList {

    List<FundamentalPieceDTO> fundamentalPieceDTOs;
}
