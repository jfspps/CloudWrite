package com.example.cloudwrite.service.DTO;

import com.example.cloudwrite.JAXBModel.FundamentalPieceDTO;
import com.example.cloudwrite.JAXBModel.FundamentalPieceDTOList;
import com.example.cloudwrite.JPARepository.FundamentalPieceRepo;
import com.example.cloudwrite.api.mapper.ConceptMapper;
import com.example.cloudwrite.api.mapper.FundamentalPieceMapper;
import com.example.cloudwrite.model.FundamentalPiece;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FundamentalPieceDTOServiceImpl implements FundamentalPieceDTOService{

    private final FundamentalPieceRepo fundamentalPieceRepo;
    private final FundamentalPieceMapper fundamentalPieceMapper;
    private final ConceptMapper conceptMapper;

    public FundamentalPieceDTOServiceImpl(FundamentalPieceRepo fundamentalPieceRepo, FundamentalPieceMapper fundamentalPieceMapper, ConceptMapper conceptMapper) {
        this.fundamentalPieceRepo = fundamentalPieceRepo;
        this.fundamentalPieceMapper = fundamentalPieceMapper;
        this.conceptMapper = conceptMapper;
    }

    @Override
    public FundamentalPieceDTOList findAll() {

        // find all database POJOs
        List<FundamentalPiece> piecesOnDB = fundamentalPieceRepo.findAll();

        // initialise the DTOs w/o Concept lists
        List<FundamentalPieceDTO> pieceDTOList = fundamentalPieceRepo
                .findAll()
                .stream()
                .map(fundamentalPieceMapper::funPieceToFunPieceDTO)
                .collect(Collectors.toList());

        FundamentalPieceDTOList list = new FundamentalPieceDTOList();
        list.getFundamentalPieces().addAll(pieceDTOList);

        return list;
    }

    @Override
    public FundamentalPieceDTOList findAllByKeyword(String keyword) {

        // find, by keyword, database POJOs
        List<FundamentalPiece> piecesOnDB = fundamentalPieceRepo.findAllByKeywordContainingIgnoreCase(keyword);

        // initialise the DTOs w/o Concept lists, by keyword
        List<FundamentalPieceDTO> pieceDTOList = fundamentalPieceRepo
                .findAllByKeywordContainingIgnoreCase(keyword)
                .stream()
                .map(fundamentalPieceMapper::funPieceToFunPieceDTO)
                .collect(Collectors.toList());

        FundamentalPieceDTOList list = new FundamentalPieceDTOList();
        list.getFundamentalPieces().addAll(pieceDTOList);

        return list;
    }

    @Override
    public FundamentalPieceDTO findById(Long id) {
        return fundamentalPieceRepo
                .findById(id)
                .map(fundamentalPieceMapper::funPieceToFunPieceDTO)
                .orElse(null);
    }
}
