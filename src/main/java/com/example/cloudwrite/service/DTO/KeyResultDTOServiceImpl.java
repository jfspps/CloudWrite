package com.example.cloudwrite.service.DTO;

import com.example.cloudwrite.JAXBModel.KeyResultDTO;
import com.example.cloudwrite.JPARepository.KeyResultRepo;
import com.example.cloudwrite.api.mapper.KeyResultMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KeyResultDTOServiceImpl implements KeyResultDTOService{

    private final KeyResultMapper keyResultMapper;
    private final KeyResultRepo keyResultRepo;

    public KeyResultDTOServiceImpl(KeyResultMapper keyResultMapper, KeyResultRepo keyResultRepo) {
        this.keyResultMapper = keyResultMapper;
        this.keyResultRepo = keyResultRepo;
    }

    @Override
    public List<KeyResultDTO> findAll() {
        return keyResultRepo
                .findAll()
                .stream()
                .map(keyResultMapper::keyResultToKeyResultDTO)
                .collect(Collectors.toList());
    }

    @Override
    public KeyResultDTO findById(Long id) {
        return keyResultRepo.findById(id)
                .map(keyResultMapper::keyResultToKeyResultDTO)
                .orElse(null);
    }
}
