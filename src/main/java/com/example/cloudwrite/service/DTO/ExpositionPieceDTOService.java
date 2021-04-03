package com.example.cloudwrite.service.DTO;

import com.example.cloudwrite.api.model.ExpositionPieceDTO;

import java.util.List;

public interface ExpositionPieceDTOService {
    List<ExpositionPieceDTO> findAll();

    List<ExpositionPieceDTO> findAllByKeyword(String keyword);

    ExpositionPieceDTO findById(String id);
}
