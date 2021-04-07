package com.example.cloudwrite.service.DTO;


import com.example.cloudwrite.JAXBModel.KeyResultDTO;

import java.util.List;

public interface KeyResultDTOService {

    List<KeyResultDTO> findAll();

    KeyResultDTO findById(Long id);
}
