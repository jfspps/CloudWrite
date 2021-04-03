package com.example.cloudwrite.service.DTO;

import com.example.cloudwrite.api.model.FundamentalPieceDTO;
import com.example.cloudwrite.api.model.FundamentalPieceDTOList;

import java.util.List;

public interface FundamentalPieceDTOService {
    FundamentalPieceDTOList findAll();

    FundamentalPieceDTOList findAllByKeyword(String keyword);

    FundamentalPieceDTO findById(Long id);
}
