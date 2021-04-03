package com.example.cloudwrite.service.DTO;

import com.example.cloudwrite.api.model.CitationDTO;

import java.util.List;

public interface CitationDTOService {
    List<CitationDTO> findAll();

    CitationDTO findById(String id);
}
