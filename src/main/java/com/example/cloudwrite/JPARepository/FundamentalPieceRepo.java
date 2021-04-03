package com.example.cloudwrite.JPARepository;

import com.example.cloudwrite.model.ExpositionPiece;
import com.example.cloudwrite.model.FundamentalPiece;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface FundamentalPieceRepo extends JpaRepository<FundamentalPiece, Long> {

    List<FundamentalPiece> findAllByTitle(String title);

    List<FundamentalPiece> findAllByKeywordContainingIgnoreCase(String keyword);
}
