package com.example.cloudwrite.service.DTO;

import com.example.cloudwrite.JPARepository.FundamentalPieceRepo;
import com.example.cloudwrite.api.mapper.FundamentalPieceMapper;
import com.example.cloudwrite.api.model.FundamentalPieceDTO;

import java.util.List;
import java.util.stream.Collectors;

public class FundamentalPieceDTOServiceImpl implements FundamentalPieceDTOService{

    private final FundamentalPieceRepo fundamentalPieceRepo;
    private final FundamentalPieceMapper fundamentalPieceMapper;

    public FundamentalPieceDTOServiceImpl(FundamentalPieceRepo fundamentalPieceRepo, FundamentalPieceMapper fundamentalPieceMapper) {
        this.fundamentalPieceRepo = fundamentalPieceRepo;
        this.fundamentalPieceMapper = fundamentalPieceMapper;
    }

    @Override
    public List<FundamentalPieceDTO> findAll() {
        return fundamentalPieceRepo
                .findAll()
                .stream()
                .map(fundamentalPieceMapper::funPieceToFunPieceDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<FundamentalPieceDTO> findAllByKeyword(String keyword) {
        return fundamentalPieceRepo
                .findAllByKeywordContainingIgnoreCase(keyword)
                .stream()
                .map(fundamentalPieceMapper::funPieceToFunPieceDTO)
                .collect(Collectors.toList());
    }

    @Override
    public FundamentalPieceDTO findById(Long id) {
        return fundamentalPieceRepo
                .findById(id)
                .map(fundamentalPieceMapper::funPieceToFunPieceDTO)
                .orElse(null);
    }
}
