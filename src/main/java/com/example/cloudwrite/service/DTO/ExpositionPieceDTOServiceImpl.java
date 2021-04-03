package com.example.cloudwrite.service.DTO;

import com.example.cloudwrite.JPARepository.ExpositionPieceRepo;
import com.example.cloudwrite.api.mapper.ExpositionPieceMapper;
import com.example.cloudwrite.api.model.ExpositionPieceDTO;
import com.example.cloudwrite.api.model.FundamentalPieceDTO;

import java.util.List;
import java.util.stream.Collectors;

public class ExpositionPieceDTOServiceImpl implements ExpositionPieceDTOService{

    private final ExpositionPieceMapper expositionPieceMapper;
    private final ExpositionPieceRepo expositionPieceRepo;

    public ExpositionPieceDTOServiceImpl(ExpositionPieceMapper expositionPieceMapper, ExpositionPieceRepo expositionPieceRepo) {
        this.expositionPieceMapper = expositionPieceMapper;
        this.expositionPieceRepo = expositionPieceRepo;
    }

    @Override
    public List<ExpositionPieceDTO> findAll() {
        return expositionPieceRepo
                .findAll()
                .stream()
                .map(expositionPieceMapper::expoPieceToExpoPieceDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ExpositionPieceDTO> findAllByKeyword(String keyword) {
        return expositionPieceRepo
                .findAllByKeywordContainingIgnoreCase(keyword)
                .stream()
                .map(expositionPieceMapper::expoPieceToExpoPieceDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ExpositionPieceDTO findById(Long id) {
        return expositionPieceRepo
                .findById(id)
                .map(expositionPieceMapper::expoPieceToExpoPieceDTO)
                .orElse(null);
    }
}
