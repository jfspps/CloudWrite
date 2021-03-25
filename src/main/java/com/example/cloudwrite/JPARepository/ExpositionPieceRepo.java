package com.example.cloudwrite.JPARepository;

import com.example.cloudwrite.model.ExpositionPiece;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface ExpositionPieceRepo extends JpaRepository<ExpositionPiece, Long> {

    Set<ExpositionPiece> findAllByTitle(String title);

    Set<ExpositionPiece> findAllByKeywords(List<String> keywords);
}
