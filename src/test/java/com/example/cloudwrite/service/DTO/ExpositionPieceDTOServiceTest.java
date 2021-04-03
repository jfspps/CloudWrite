package com.example.cloudwrite.service.DTO;

import com.example.cloudwrite.JPARepository.ExpositionPieceRepo;
import com.example.cloudwrite.api.mapper.ExpositionPieceMapper;
import com.example.cloudwrite.api.model.ExpositionPieceDTO;
import com.example.cloudwrite.model.ExpositionPiece;
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
class ExpositionPieceDTOServiceTest {

    private ExpositionPieceDTOService expositionPieceDTOService;
    private ExpositionPiece expositionPiece;

    @Mock
    private ExpositionPieceRepo expositionPieceRepo;

    @BeforeEach
    void setUp() {
        expositionPieceDTOService = new ExpositionPieceDTOServiceImpl(ExpositionPieceMapper.INSTANCE, expositionPieceRepo);
        expositionPiece = ExpositionPiece.builder().keyword("magicWords").build();
    }

    @Test
    void findAll() {
        // define what is returned through JPA repo (given)
        List<ExpositionPiece> expositionPieces = Arrays.asList(new ExpositionPiece(), new ExpositionPiece());
        when(expositionPieceRepo.findAll()).thenReturn(expositionPieces);

        // call the DTO service interface (when) as defined by its implementation class
        List<ExpositionPieceDTO> expositionPieceDTOS = expositionPieceDTOService.findAll();

        // check mapping (then)
        assertEquals(2, expositionPieceDTOS.size());
    }

    @Test
    void findAllByKeyword() {
        when(expositionPieceRepo.findAllByKeywordContainingIgnoreCase(anyString())).thenReturn(Arrays.asList(expositionPiece, new ExpositionPiece()));

        List<ExpositionPieceDTO> expositionPieceDTOS = expositionPieceDTOService.findAllByKeyword("lafjdlfkj");

        assertEquals(2, expositionPieceDTOS.size());
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