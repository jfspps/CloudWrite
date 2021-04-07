package com.example.cloudwrite.service.DTO;


import com.example.cloudwrite.JAXBModel.ConceptDTO;

import java.util.List;

public interface ConceptDTOService {
    List<ConceptDTO> findAll();

    ConceptDTO findById(Long id);
}
