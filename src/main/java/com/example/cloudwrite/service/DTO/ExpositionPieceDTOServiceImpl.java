package com.example.cloudwrite.service.DTO;

import com.example.cloudwrite.JAXBModel.ExpositionPieceDTO;
import com.example.cloudwrite.JAXBModel.ExpositionPieceDTOList;
import com.example.cloudwrite.JPARepository.ExpositionPieceRepo;
import com.example.cloudwrite.api.mapper.CitationMapper;
import com.example.cloudwrite.api.mapper.ExpositionPieceMapper;
import com.example.cloudwrite.api.mapper.KeyResultMapper;
import com.example.cloudwrite.api.mapper.StandfirstMapper;
import com.example.cloudwrite.model.ExpositionPiece;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpositionPieceDTOServiceImpl implements ExpositionPieceDTOService{

    private final ExpositionPieceMapper expositionPieceMapper;
    private final KeyResultMapper keyResultMapper;
    private final CitationMapper citationMapper;
    private final StandfirstMapper standfirstMapper;
    private final ExpositionPieceRepo expositionPieceRepo;

    public ExpositionPieceDTOServiceImpl(ExpositionPieceMapper expositionPieceMapper,
                                         KeyResultMapper keyResultMapper,
                                         CitationMapper citationMapper,
                                         StandfirstMapper standfirstMapper,
                                         ExpositionPieceRepo expositionPieceRepo) {
        this.expositionPieceMapper = expositionPieceMapper;
        this.keyResultMapper = keyResultMapper;
        this.citationMapper = citationMapper;
        this.standfirstMapper = standfirstMapper;
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
        return addCitationsAndKeyResults(piecesOnDB, pieceDTOList);
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
        return addCitationsAndKeyResults(piecesOnDB, pieceDTOList);
    }

    @Override
    public ExpositionPieceDTO findById(Long id) {
        return expositionPieceRepo
                .findById(id)
                .map(expositionPieceMapper::expoPieceToExpoPieceDTO)
                .orElse(null);
    }

    private ExpositionPieceDTOList addCitationsAndKeyResults(List<ExpositionPiece> piecesOnDB, List<ExpositionPieceDTO> pieceDTOList) {
        for (int pieceID = 0; pieceID < piecesOnDB.size(); pieceID++){
            ExpositionPiece currentPieceOnDB = piecesOnDB.get(pieceID);
            ExpositionPieceDTO currentPieceDTO = pieceDTOList.get(pieceID);

            currentPieceOnDB.getKeyResults()
                    .stream()
                    .map(keyResultMapper::keyResultToKeyResultDTO)
                    .forEach(keyResultDTO -> currentPieceDTO.getKeyResultDTOList().getKeyResultDTOs().add(keyResultDTO));

            currentPieceOnDB.getCitations()
                    .stream()
                    .map(citationMapper::citationToCitationDTO)
                    .forEach(citationDTO -> currentPieceDTO.getCitationDTOList().getCitationDTOs().add(citationDTO));

            currentPieceDTO.setStandfirstDTO(standfirstMapper.standfirstToStandfirstDTO(currentPieceOnDB.getStandfirst()));
        }
        ExpositionPieceDTOList list = new ExpositionPieceDTOList();
        list.getExpositionPiece().addAll(pieceDTOList);

        return list;
    }
}
