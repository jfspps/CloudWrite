package com.example.cloudwrite.JPARepository;

import com.example.cloudwrite.model.ResearchPiece;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResearchPieceRepo extends JpaRepository<ResearchPiece, Long> {

    List<ResearchPiece> findAllByTitle(String title);

    List<ResearchPiece> findAllByKeywordContainingIgnoreCase(String keyword);
}
