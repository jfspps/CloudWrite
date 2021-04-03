package com.example.cloudwrite.service;

import com.example.cloudwrite.model.ExpositionPiece;

import java.util.List;
import java.util.Set;

public interface ExpositionPieceService extends BaseService<ExpositionPiece, Long> {

    List<ExpositionPiece> findAllByKeyword(String keyword);

    List<ExpositionPiece> findAllByTitle(String title);
}
