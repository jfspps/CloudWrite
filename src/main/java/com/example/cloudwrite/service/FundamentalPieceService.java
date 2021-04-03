package com.example.cloudwrite.service;

import com.example.cloudwrite.model.ExpositionPiece;
import com.example.cloudwrite.model.FundamentalPiece;

import java.util.List;
import java.util.Set;

public interface FundamentalPieceService extends BaseService<FundamentalPiece, Long> {

    List<FundamentalPiece> findAllByKeyword(String keyword);

    List<FundamentalPiece> findAllByTitle(String title);
}
