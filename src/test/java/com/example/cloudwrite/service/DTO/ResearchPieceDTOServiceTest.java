package com.example.cloudwrite.service.DTO;

import com.example.cloudwrite.JAXBModel.ResearchPieceDTO;
import com.example.cloudwrite.JAXBModel.ResearchPieceDTOList;
import com.example.cloudwrite.JPARepository.ResearchPieceRepo;
import com.example.cloudwrite.api.mapper.CitationMapper;
import com.example.cloudwrite.api.mapper.ResearchPieceMapper;
import com.example.cloudwrite.api.mapper.KeyResultMapper;
import com.example.cloudwrite.api.mapper.StandfirstMapper;
import com.example.cloudwrite.model.Citation;
import com.example.cloudwrite.model.ResearchPiece;
import com.example.cloudwrite.model.KeyResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ResearchPieceDTOServiceTest {

    private ResearchPieceDTOService researchPieceDTOService;
    private ResearchPiece researchPiece;
    private KeyResult keyResult;
    private Citation citation;

    @Mock
    private ResearchPieceRepo researchPieceRepo;

    @BeforeEach
    void setUp() {
        keyResult = KeyResult.builder().description("key result desc.").build();
        citation = Citation.builder().reference("some citation").build();
        researchPiece = ResearchPiece.builder()
                .keyword("magicWords")
                .keyResults(Collections.singletonList(keyResult))
                .citations(Collections.singletonList(citation))
                .build();

        researchPieceDTOService = new ResearchPieceDTOServiceImpl(
                ResearchPieceMapper.INSTANCE, KeyResultMapper.INSTANCE, CitationMapper.INSTANCE, StandfirstMapper.INSTANCE, researchPieceRepo);
    }

    @Test
    void findAll() {
        // define what is returned through JPA repo (given)
        List<ResearchPiece> researchPieces = Collections.singletonList(researchPiece);
        when(researchPieceRepo.findAll()).thenReturn(researchPieces);

        // call the DTO service interface (when) as defined by its implementation class
        ResearchPieceDTOList researchPieceDTOS = researchPieceDTOService.findAll();

        // check mapping (then)
        assertEquals(1, researchPieceDTOS.getResearchPiece().size());
    }

    @Test
    void findAllByKeyword() {
        List<ResearchPiece> researchPieces = Collections.singletonList(researchPiece);
        when(researchPieceRepo.findAllByKeywordContainingIgnoreCase(anyString())).thenReturn(researchPieces);

        ResearchPieceDTOList researchPieceDTOS = researchPieceDTOService.findAllByKeyword("lafjdlfkj");

        assertEquals(1, researchPieceDTOS.getResearchPiece().size());
    }

    @Test
    void findById() {
        when(researchPieceRepo.findById(anyLong())).thenReturn(Optional.of(researchPiece));

        // call the DTO service interface (when) as defined by its implementation class
        ResearchPieceDTO found = researchPieceDTOService.findById(3L);

        // check mapping (then)
        assertEquals("magicWords", found.getKeyword());
    }
}