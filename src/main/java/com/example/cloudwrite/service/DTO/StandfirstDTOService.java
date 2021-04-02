package com.example.cloudwrite.service.DTO;

import com.example.cloudwrite.api.model.StandfirstDTO;

import java.util.List;

// no need to extend by BaseService and have to impl all non-API CRUD methods
public interface StandfirstDTOService {

    List<StandfirstDTO> findAll();
}
