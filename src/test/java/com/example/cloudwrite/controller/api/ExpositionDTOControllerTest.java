package com.example.cloudwrite.controller.api;

import com.example.cloudwrite.JAXBModel.ExpositionPieceDTO;
import com.example.cloudwrite.JAXBModel.ExpositionPieceDTOList;
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

    // inject mocks into the controller automatically
    @InjectMocks
    ExpositionDTOController expositionDTOController;

    MockMvc mockMvc;

    ExpositionPieceDTO expositionPieceDTO1 = new ExpositionPieceDTO();
    ExpositionPieceDTO expositionPieceDTO2 = new ExpositionPieceDTO();
    ExpositionPieceDTOList pieceDTOS;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(expositionDTOController).build();

        expositionPieceDTO1.setId(1L);
        expositionPieceDTO1.setKeyword(KEYWORD);

        expositionPieceDTO2.setId(2L);
        expositionPieceDTO2.setKeyword(KEYWORD + "sss");
        pieceDTOS = new ExpositionPieceDTOList();
        pieceDTOS.getExpositionPiece().addAll(Arrays.asList(expositionPieceDTO1, expositionPieceDTO2));
    }

    // these test expect JSON returns instead of XML returns

    @Test
    void getAllExpoPieces() throws Exception {
        when(expositionPieceDTOService.findAll()).thenReturn(pieceDTOS);

        mockMvc.perform(get("/api/expositions/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.expositionPiece", hasSize(2))); // $ is root, followed by DTO properties
    }

    @Test
    void getExpoPiecesByKeyword() throws Exception {
        when(expositionPieceDTOService.findAllByKeyword(anyString())).thenReturn(pieceDTOS);

        mockMvc.perform(get("/api/expositions/cocoa/search")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.expositionPiece", hasSize(2)));
    }
}