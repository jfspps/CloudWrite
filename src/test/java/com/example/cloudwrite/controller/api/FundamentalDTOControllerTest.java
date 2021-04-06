package com.example.cloudwrite.controller.api;

import com.example.cloudwrite.api.model.FundamentalPieceDTO;
import com.example.cloudwrite.api.model.FundamentalPieceDTOList;
import com.example.cloudwrite.service.DTO.FundamentalPieceDTOService;
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
class FundamentalDTOControllerTest {

    private final String KEYWORD = "theTests";

    @Mock
    FundamentalPieceDTOService fundamentalPieceDTOService;

    // inject mocks into the controller automatically
    @InjectMocks
    FundamentalDTOController fundamentalDTOController;

    MockMvc mockMvc;

    FundamentalPieceDTO fundamentalPieceDTO = new FundamentalPieceDTO();
    FundamentalPieceDTO fundamentalPieceDTO1 = new FundamentalPieceDTO();
    FundamentalPieceDTOList pieceDTOS_long;
    FundamentalPieceDTOList pieceDTOS_short;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(fundamentalDTOController).build();

        fundamentalPieceDTO.setId(1L);
        fundamentalPieceDTO.setKeyword(KEYWORD);

        fundamentalPieceDTO1.setId(2L);
        fundamentalPieceDTO1.setKeyword(KEYWORD + "sss");

        pieceDTOS_long = new FundamentalPieceDTOList(Arrays.asList(fundamentalPieceDTO, fundamentalPieceDTO1));
        pieceDTOS_short = new FundamentalPieceDTOList(Collections.singletonList(fundamentalPieceDTO));
    }

    // these test expect JSON returns instead of XML returns

    @Test
    void getAllFunPieces() throws Exception {
        when(fundamentalPieceDTOService.findAll()).thenReturn(pieceDTOS_long);

        mockMvc.perform(get("/api/fundamentals/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fundamentalPieces", hasSize(2))); // $ is root, followed by DTO properties
    }

    @Test
    void getFunPiecesByKeyword() throws Exception {
        when(fundamentalPieceDTOService.findAllByKeyword(anyString())).thenReturn(pieceDTOS_short);

        mockMvc.perform(get("/api/fundamentals/cocoa/search")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fundamentalPieces", hasSize(1)));
    }
}