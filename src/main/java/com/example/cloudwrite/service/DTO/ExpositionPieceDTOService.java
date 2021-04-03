package com.example.cloudwrite.service.DTO;

import com.example.cloudwrite.api.model.ExpositionPieceDTO;
import com.example.cloudwrite.api.model.ExpositionPieceDTOList;

import java.util.List;

public interface ExpositionPieceDTOService {
    ExpositionPieceDTOList findAll();

    ExpositionPieceDTOList findAllByKeyword(String keyword);

    ExpositionPieceDTO findById(Long id);
}
