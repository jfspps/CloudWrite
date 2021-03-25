package com.example.cloudwrite.service;

import com.example.cloudwrite.model.ExpositionPiece;

import java.util.List;
import java.util.Set;

public interface ExpositionPieceService extends BaseService<ExpositionPiece, Long> {

    Set<ExpositionPiece> findAllByKeyword(String keyword);

    Set<ExpositionPiece> findAllByTitle(String title);
}
