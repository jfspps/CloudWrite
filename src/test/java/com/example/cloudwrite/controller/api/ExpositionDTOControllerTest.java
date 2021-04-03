package com.example.cloudwrite.controller.api;

import com.example.cloudwrite.api.model.ExpositionPieceDTO;
import com.example.cloudwrite.api.model.ExpositionPieceDTOList;
import com.example.cloudwrite.service.DTO.ExpositionPieceDTOService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ExpositionDTOControllerTest {

    private final String KEYWORD = "theTests";

    @Mock
    ExpositionPieceDTOService expositionPieceDTOService;

    @InjectMocks
    ExpositionDTOController expositionDTOController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(expositionDTOController).build();
    }

    @Test
    void getAllExpoPieces() throws Exception {
        ExpositionPieceDTO expositionPieceDTO1 = new ExpositionPieceDTO();
        expositionPieceDTO1.setId(1L);
        expositionPieceDTO1.setKeyword(KEYWORD);

        ExpositionPieceDTO expositionPieceDTO2 = new ExpositionPieceDTO();
        expositionPieceDTO2.setId(2L);
        expositionPieceDTO2.setKeyword(KEYWORD + "sss");

        ExpositionPieceDTOList pieceDTOS = new ExpositionPieceDTOList(Arrays.asList(expositionPieceDTO1, expositionPieceDTO2));

        when(expositionPieceDTOService.findAll()).thenReturn(pieceDTOS);

        mockMvc.perform(get("/api/expositions/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.expositionPieceDTOS", hasSize(2))); // $ is root, followed by DTO properties
    }

    @Test
    void getExpoPiecesByKeyword() throws Exception {
        ExpositionPieceDTO expositionPieceDTO1 = new ExpositionPieceDTO();
        expositionPieceDTO1.setId(1L);
        expositionPieceDTO1.setKeyword(KEYWORD);

        ExpositionPieceDTOList pieceDTOS = new ExpositionPieceDTOList(Collections.singletonList(expositionPieceDTO1));

        when(expositionPieceDTOService.findAllByKeyword(anyString())).thenReturn(pieceDTOS);

        mockMvc.perform(get("/api/expositions/cocoa/search")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.expositionPieceDTOS", hasSize(1)));
    }
}