package com.example.cloudwrite.service.DTO;

import com.example.cloudwrite.api.model.ExpositionPieceDTO;
import com.example.cloudwrite.api.model.ExpositionPieceDTOList;

public interface ExpositionPieceDTOService {
    ExpositionPieceDTOList findAll();

    ExpositionPieceDTOList findAllByKeyword(String keyword);

    ExpositionPieceDTO findById(Long id);
}
