package com.example.cloudwrite.service.DTO;

import com.example.cloudwrite.JPARepository.CitationRepo;
import com.example.cloudwrite.JPARepository.ExpositionPieceRepo;
import com.example.cloudwrite.JPARepository.KeyResultRepo;
import com.example.cloudwrite.api.mapper.CitationMapper;
import com.example.cloudwrite.api.mapper.ExpositionPieceMapper;
import com.example.cloudwrite.api.mapper.KeyResultMapper;
import com.example.cloudwrite.api.model.*;
import com.example.cloudwrite.model.Citation;
import com.example.cloudwrite.model.ExpositionPiece;
import com.example.cloudwrite.model.KeyResult;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpositionPieceDTOServiceImpl implements ExpositionPieceDTOService{

    private final ExpositionPieceMapper expositionPieceMapper;
    private final KeyResultMapper keyResultMapper;
    private final CitationMapper citationMapper;
    private final ExpositionPieceRepo expositionPieceRepo;

    public ExpositionPieceDTOServiceImpl(ExpositionPieceMapper expositionPieceMapper, KeyResultMapper keyResultMapper, CitationMapper citationMapper, ExpositionPieceRepo expositionPieceRepo) {
        this.expositionPieceMapper = expositionPieceMapper;
        this.keyResultMapper = keyResultMapper;
        this.citationMapper = citationMapper;
        this.expositionPieceRepo = expositionPieceRepo;
    }

    @Override
    public ExpositionPieceDTOList findAll() {

        // find all database POJOs
        List<ExpositionPiece> piecesOnDB = expositionPieceRepo.findAll();

        // initialise the DTOs w/o keyResult and Citation lists
        List<ExpositionPieceDTO> pieceDTOList = expositionPieceRepo
                .findAll()
                .stream()
                .map(expositionPieceMapper::expoPieceToExpoPieceDTO)
                .collect(Collectors.toList());

        // sync DTO with POJOs and return
        return buildExpoPieceDTOList(piecesOnDB, pieceDTOList);
    }

    @Override
    public ExpositionPieceDTOList findAllByKeyword(String keyword) {

        // find, by keyword, database POJOs
        List<ExpositionPiece> piecesOnDB = expositionPieceRepo.findAllByKeywordContainingIgnoreCase(keyword);

        // initialise the DTOs w/o keyResult and Citation lists, by keyword
        List<ExpositionPieceDTO> pieceDTOList = expositionPieceRepo
                .findAllByKeywordContainingIgnoreCase(keyword)
                .stream()
                .map(expositionPieceMapper::expoPieceToExpoPieceDTO)
                .collect(Collectors.toList());

        // sync DTO with POJOs and return
        return buildExpoPieceDTOList(piecesOnDB, pieceDTOList);
    }

    @Override
    public ExpositionPieceDTO findById(Long id) {
        return expositionPieceRepo
                .findById(id)
                .map(expositionPieceMapper::expoPieceToExpoPieceDTO)
                .orElse(null);
    }

    private ExpositionPieceDTOList buildExpoPieceDTOList(List<ExpositionPiece> piecesOnDB, List<ExpositionPieceDTO> pieceDTOList) {
        for (int pieceID = 0; pieceID < piecesOnDB.size(); pieceID++){
            ExpositionPiece currentPiece = piecesOnDB.get(pieceID);

            List<KeyResultDTO> keyResultDTOS = currentPiece.getKeyResults()
                    .stream()
                    .map(keyResultMapper::keyResultToKeyResultDTO)
                    .collect(Collectors.toList());
            List<CitationDTO> citationDTOS = currentPiece.getCitations()
                    .stream()
                    .map(citationMapper::citationToCitationDTO)
                    .collect(Collectors.toList());

            pieceDTOList.get(pieceID).setKeyResultDTOList(new KeyResultDTOList(keyResultDTOS));
            pieceDTOList.get(pieceID).setCitationDTOList(new CitationDTOList(citationDTOS));
        }

        return new ExpositionPieceDTOList(pieceDTOList);
    }
}
