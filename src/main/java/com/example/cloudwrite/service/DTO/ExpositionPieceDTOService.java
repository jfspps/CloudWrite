package com.example.cloudwrite.service.DTO;


import com.example.cloudwrite.JAXBModel.ExpositionPieceDTO;
import com.example.cloudwrite.JAXBModel.ExpositionPieceDTOList;

public interface ExpositionPieceDTOService {
    ExpositionPieceDTOList findAll();

    ExpositionPieceDTOList findAllByKeyword(String keyword);

    ExpositionPieceDTO findById(Long id);
}
