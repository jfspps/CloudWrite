package com.example.cloudwrite.service.DTO;

import com.example.cloudwrite.JPARepository.FundamentalPieceRepo;
import com.example.cloudwrite.api.mapper.FundamentalPieceMapper;
import com.example.cloudwrite.api.model.FundamentalPieceDTO;
import com.example.cloudwrite.model.FundamentalPiece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
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

    @Mock
    private FundamentalPieceRepo fundamentalPieceRepo;

    @BeforeEach
    void setUp() {
        fundamentalPieceDTOService = new FundamentalPieceDTOServiceImpl(fundamentalPieceRepo, FundamentalPieceMapper.INSTANCE);
        fundamentalPiece = FundamentalPiece.builder().keyword("magicWords").build();
    }

    @Test
    void findAll() {
        // define what is returned through JPA repo (given)
        List<FundamentalPiece> fundamentalPieces = Arrays.asList(new FundamentalPiece(), new FundamentalPiece());
        when(fundamentalPieceRepo.findAll()).thenReturn(fundamentalPieces);

        // call the DTO service interface (when) as defined by its implementation class
        List<FundamentalPieceDTO> fundamentalPieceDTOS = fundamentalPieceDTOService.findAll();

        // check mapping (then)
        assertEquals(2, fundamentalPieceDTOS.size());
    }

    @Test
    void findAllByKeyword() {
        when(fundamentalPieceRepo.findAllByKeywordContainingIgnoreCase(anyString())).thenReturn(Arrays.asList(fundamentalPiece, new FundamentalPiece()));

        List<FundamentalPieceDTO> found = fundamentalPieceDTOService.findAllByKeyword("lafjdlfkj");

        assertEquals(2, found.size());
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