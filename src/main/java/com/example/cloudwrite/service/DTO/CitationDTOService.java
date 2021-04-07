package com.example.cloudwrite.service.DTO;


import com.example.cloudwrite.JAXBModel.CitationDTO;

import java.util.List;

public interface CitationDTOService {
    List<CitationDTO> findAll();

    CitationDTO findById(Long id);
}
