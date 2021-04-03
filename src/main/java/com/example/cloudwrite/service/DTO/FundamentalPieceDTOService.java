package com.example.cloudwrite.service.DTO;

import com.example.cloudwrite.api.model.FundamentalPieceDTO;

import java.util.List;

public interface FundamentalPieceDTOService {
    List<FundamentalPieceDTO> findAll();

    List<FundamentalPieceDTO> findAllByKeyword(String keyword);

    FundamentalPieceDTO findById(String id);
}
