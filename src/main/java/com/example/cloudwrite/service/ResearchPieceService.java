package com.example.cloudwrite.service;

import com.example.cloudwrite.model.ResearchPiece;

import java.util.List;

public interface ResearchPieceService extends BaseService<ResearchPiece, Long> {

    List<ResearchPiece> findAllByKeyword(String keyword);

    List<ResearchPiece> findAllByTitle(String title);
}
