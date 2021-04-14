package com.example.cloudwrite.service.DTO;


import com.example.cloudwrite.JAXBModel.ResearchPieceDTO;
import com.example.cloudwrite.JAXBModel.ResearchPieceDTOList;

public interface ResearchPieceDTOService {
    ResearchPieceDTOList findAll();

    ResearchPieceDTOList findAllByKeyword(String keyword);

    ResearchPieceDTO findById(Long id);
}
