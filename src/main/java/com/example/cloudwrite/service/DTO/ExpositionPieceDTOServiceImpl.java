package com.example.cloudwrite.service.DTO;

import com.example.cloudwrite.JPARepository.ExpositionPieceRepo;
import com.example.cloudwrite.api.mapper.ExpositionPieceMapper;
import com.example.cloudwrite.api.model.ExpositionPieceDTO;
import com.example.cloudwrite.api.model.ExpositionPieceDTOList;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpositionPieceDTOServiceImpl implements ExpositionPieceDTOService{

    private final ExpositionPieceMapper expositionPieceMapper;
    private final ExpositionPieceRepo expositionPieceRepo;

    public ExpositionPieceDTOServiceImpl(ExpositionPieceMapper expositionPieceMapper, ExpositionPieceRepo expositionPieceRepo) {
        this.expositionPieceMapper = expositionPieceMapper;
        this.expositionPieceRepo = expositionPieceRepo;
    }

    @Override
    public ExpositionPieceDTOList findAll() {
        List<ExpositionPieceDTO> pieceDTOList = expositionPieceRepo
                .findAll()
                .stream()
                .map(expositionPieceMapper::expoPieceToExpoPieceDTO)
                .collect(Collectors.toList());

        return new ExpositionPieceDTOList(pieceDTOList);
    }

    @Override
    public ExpositionPieceDTOList findAllByKeyword(String keyword) {
        List<ExpositionPieceDTO> pieceDTOList = expositionPieceRepo
                .findAllByKeywordContainingIgnoreCase(keyword)
                .stream()
                .map(expositionPieceMapper::expoPieceToExpoPieceDTO)
                .collect(Collectors.toList());

        return new ExpositionPieceDTOList(pieceDTOList);
    }

    @Override
    public ExpositionPieceDTO findById(Long id) {
        return expositionPieceRepo
                .findById(id)
                .map(expositionPieceMapper::expoPieceToExpoPieceDTO)
                .orElse(null);
    }
}
