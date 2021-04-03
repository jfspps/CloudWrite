package com.example.cloudwrite.service.DTO;

import com.example.cloudwrite.api.model.ConceptDTO;

import java.util.List;

public interface ConceptDTOService {
    List<ConceptDTO> findAll();

    ConceptDTO findById(Long id);
}
