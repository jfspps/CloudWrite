package com.example.cloudwrite.JPARepository;

import com.example.cloudwrite.model.Concept;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConceptRepo extends JpaRepository<Concept, Long> {
}
