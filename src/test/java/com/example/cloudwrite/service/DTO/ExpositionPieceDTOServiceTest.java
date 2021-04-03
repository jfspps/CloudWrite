package com.example.cloudwrite.service.DTO;

import com.example.cloudwrite.JPARepository.ExpositionPieceRepo;
import com.example.cloudwrite.api.mapper.CitationMapper;
import com.example.cloudwrite.api.mapper.ExpositionPieceMapper;
import com.example.cloudwrite.api.mapper.KeyResultMapper;
import com.example.cloudwrite.api.model.ExpositionPieceDTO;
import com.example.cloudwrite.api.model.ExpositionPieceDTOList;
import com.example.cloudwrite.model.Citation;
import com.example.cloudwrite.model.ExpositionPiece;
import com.example.cloudwrite.model.KeyResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExpositionPieceDTOServiceTest {

    private ExpositionPieceDTOService expositionPieceDTOService;
    private ExpositionPiece expositionPiece;
    private KeyResult keyResult;
    private Citation citation;

    @Mock
    private ExpositionPieceRepo expositionPieceRepo;

    @BeforeEach
    void setUp() {
        keyResult = KeyResult.builder().description("key result desc.").build();
        citation = Citation.builder().ref("some citation").build();
        expositionPiece = ExpositionPiece.builder()
                .keyword("magicWords")
                .keyResults(Collections.singletonList(keyResult))
                .citations(Collections.singletonList(citation))
                .build();

        expositionPieceDTOService = new ExpositionPieceDTOServiceImpl(
                ExpositionPieceMapper.INSTANCE, KeyResultMapper.INSTANCE, CitationMapper.INSTANCE, expositionPieceRepo);
    }

    @Test
    void findAll() {
        // define what is returned through JPA repo (given)
        List<ExpositionPiece> expositionPieces = Collections.singletonList(expositionPiece);
        when(expositionPieceRepo.findAll()).thenReturn(expositionPieces);

        // call the DTO service interface (when) as defined by its implementation class
        ExpositionPieceDTOList expositionPieceDTOS = expositionPieceDTOService.findAll();

        // check mapping (then)
        assertEquals(1, expositionPieceDTOS.getExpositionPieceDTOS().size());
    }

    @Test
    void findAllByKeyword() {
        List<ExpositionPiece> expositionPieces = Collections.singletonList(expositionPiece);
        when(expositionPieceRepo.findAllByKeywordContainingIgnoreCase(anyString())).thenReturn(expositionPieces);

        ExpositionPieceDTOList expositionPieceDTOS = expositionPieceDTOService.findAllByKeyword("lafjdlfkj");

        assertEquals(1, expositionPieceDTOS.getExpositionPieceDTOS().size());
    }

    @Test
    void findById() {
        when(expositionPieceRepo.findById(anyLong())).thenReturn(Optional.of(expositionPiece));

        // call the DTO service interface (when) as defined by its implementation class
        ExpositionPieceDTO found = expositionPieceDTOService.findById(3L);

        // check mapping (then)
        assertEquals("magicWords", found.getKeyword());
    }
}