package com.example.cloudwrite.JPARepository;

import com.example.cloudwrite.model.Citation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CitationRepo extends JpaRepository<Citation, Long> {
}
