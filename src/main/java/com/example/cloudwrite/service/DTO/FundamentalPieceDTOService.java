package com.example.cloudwrite.service.DTO;


import com.example.cloudwrite.JAXBModel.FundamentalPieceDTO;
import com.example.cloudwrite.JAXBModel.FundamentalPieceDTOList;

public interface FundamentalPieceDTOService {
    FundamentalPieceDTOList findAll();

    FundamentalPieceDTOList findAllByKeyword(String keyword);

    FundamentalPieceDTO findById(Long id);
}
