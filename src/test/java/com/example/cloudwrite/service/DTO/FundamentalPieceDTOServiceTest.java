package com.example.cloudwrite.service.DTO;

import com.example.cloudwrite.JAXBModel.FundamentalPieceDTO;
import com.example.cloudwrite.JAXBModel.FundamentalPieceDTOList;
import com.example.cloudwrite.JPARepository.FundamentalPieceRepo;
import com.example.cloudwrite.api.mapper.ConceptMapper;
import com.example.cloudwrite.api.mapper.FundamentalPieceMapper;
import com.example.cloudwrite.model.Concept;
import com.example.cloudwrite.model.FundamentalPiece;
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
class FundamentalPieceDTOServiceTest {

    private FundamentalPieceDTOService fundamentalPieceDTOService;
    private FundamentalPiece fundamentalPiece;
    private Concept concept;

    @Mock
    private FundamentalPieceRepo fundamentalPieceRepo;

    @BeforeEach
    void setUp() {
        concept = Concept.builder().description("some new concept").build();
        fundamentalPiece = FundamentalPiece.builder()
                .keyword("magicWords")
                .conceptList(Collections.singletonList(concept))
                .build();

        fundamentalPieceDTOService = new FundamentalPieceDTOServiceImpl(fundamentalPieceRepo, FundamentalPieceMapper.INSTANCE, ConceptMapper.INSTANCE);
    }

    @Test
    void findAll() {
        // define what is returned through JPA repo (given)
        List<FundamentalPiece> fundamentalPieces = Collections.singletonList(fundamentalPiece);
        when(fundamentalPieceRepo.findAll()).thenReturn(fundamentalPieces);

        // call the DTO service interface (when) as defined by its implementation class
        FundamentalPieceDTOList fundamentalPieceDTOList;
        fundamentalPieceDTOList = fundamentalPieceDTOService.findAll();

        // check mapping (then)
        assertEquals(1, fundamentalPieceDTOList.getFundamentalPiece().size());
    }

    @Test
    void findAllByKeyword() {
        List<FundamentalPiece> fundamentalPieces = Collections.singletonList(fundamentalPiece);
        when(fundamentalPieceRepo.findAllByKeywordContainingIgnoreCase(anyString())).thenReturn(Collections.singletonList(fundamentalPiece));

        FundamentalPieceDTOList found = fundamentalPieceDTOService.findAllByKeyword("lafjdlfkj");

        assertEquals(1, found.getFundamentalPiece().size());
    }

    @Test
    void findById() {
        when(fundamentalPieceRepo.findById(anyLong())).thenReturn(Optional.of(fundamentalPiece));

        // call the DTO service interface (when) as defined by its implementation class
        FundamentalPieceDTO found = fundamentalPieceDTOService.findById(3L);

        // check mapping (then)
        assertEquals("magicWords", found.getKeyword());
    }
}