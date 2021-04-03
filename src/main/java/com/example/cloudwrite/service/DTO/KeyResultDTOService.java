package com.example.cloudwrite.service.DTO;

import com.example.cloudwrite.api.model.KeyResultDTO;

import java.util.List;

public interface KeyResultDTOService {
    List<KeyResultDTO> findAll();

    KeyResultDTO findById(String id);
}
