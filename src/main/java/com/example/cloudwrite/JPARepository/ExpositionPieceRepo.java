package com.example.cloudwrite.JPARepository;

import com.example.cloudwrite.model.ExpositionPiece;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpositionPieceRepo extends JpaRepository<ExpositionPiece, Long> {

    List<ExpositionPiece> findAllByTitle(String title);

    List<ExpositionPiece> findAllByKeywordContainingIgnoreCase(String keyword);
}
