package com.example.cloudwrite.service.DTO;

import com.example.cloudwrite.JAXBModel.ResearchPieceDTO;
import com.example.cloudwrite.JAXBModel.ResearchPieceDTOList;
import com.example.cloudwrite.JPARepository.ResearchPieceRepo;
import com.example.cloudwrite.api.mapper.CitationMapper;
import com.example.cloudwrite.api.mapper.ResearchPieceMapper;
import com.example.cloudwrite.api.mapper.KeyResultMapper;
import com.example.cloudwrite.api.mapper.StandfirstMapper;
import com.example.cloudwrite.model.ResearchPiece;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResearchPieceDTOServiceImpl implements ResearchPieceDTOService {

    private final ResearchPieceMapper researchPieceMapper;
    private final KeyResultMapper keyResultMapper;
    private final CitationMapper citationMapper;
    private final StandfirstMapper standfirstMapper;
    private final ResearchPieceRepo researchPieceRepo;

    public ResearchPieceDTOServiceImpl(ResearchPieceMapper researchPieceMapper,
                                       KeyResultMapper keyResultMapper,
                                       CitationMapper citationMapper,
                                       StandfirstMapper standfirstMapper,
                                       ResearchPieceRepo researchPieceRepo) {
        this.researchPieceMapper = researchPieceMapper;
        this.keyResultMapper = keyResultMapper;
        this.citationMapper = citationMapper;
        this.standfirstMapper = standfirstMapper;
        this.researchPieceRepo = researchPieceRepo;
    }

    @Override
    public ResearchPieceDTOList findAll() {

        // find all database POJOs
        List<ResearchPiece> piecesOnDB = researchPieceRepo.findAll();

        // initialise the DTOs w/o keyResult and Citation lists
        List<ResearchPieceDTO> pieceDTOList = researchPieceRepo
                .findAll()
                .stream()
                .map(researchPieceMapper::resPieceToResPieceDTO)
                .collect(Collectors.toList());

        ResearchPieceDTOList list = new ResearchPieceDTOList();
        list.getResearchPiece().addAll(pieceDTOList);

        return list;
    }

    @Override
    public ResearchPieceDTOList findAllByKeyword(String keyword) {

        // find, by keyword, database POJOs
        List<ResearchPiece> piecesOnDB = researchPieceRepo.findAllByKeywordContainingIgnoreCase(keyword);

        // initialise the DTOs w/o keyResult and Citation lists, by keyword
        List<ResearchPieceDTO> pieceDTOList = researchPieceRepo
                .findAllByKeywordContainingIgnoreCase(keyword)
                .stream()
                .map(researchPieceMapper::resPieceToResPieceDTO)
                .collect(Collectors.toList());

        ResearchPieceDTOList list = new ResearchPieceDTOList();
        list.getResearchPiece().addAll(pieceDTOList);

        return list;
    }

    @Override
    public ResearchPieceDTO findById(Long id) {
        return researchPieceRepo
                .findById(id)
                .map(researchPieceMapper::resPieceToResPieceDTO)
                .orElse(null);
    }
}
