package com.example.cloudwrite.controller.api;

import com.example.cloudwrite.JAXBModel.ExpositionPieceDTO;
import com.example.cloudwrite.JAXBModel.ExpositionPieceDTOList;
import com.example.cloudwrite.api.mapper.ExpositionPieceMapper;
import com.example.cloudwrite.api.mapper.StandfirstMapper;
import com.example.cloudwrite.model.Citation;
import com.example.cloudwrite.model.KeyResult;
import com.example.cloudwrite.model.Standfirst;
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
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ExpositionDTOControllerTest {

    private final String TITLE = "a title";
    private final String KEYWORD = "a keyword";
    private final String PURPOSE = "a purpose";
    private final String PROGRESS = "current progress";
    private final String FUTUREWORK = "what's next";

    private final String STANDFIRSTrationale = "why this is relevant";
    private final String STANDFIRSTapproach = "how to handle it";

    private final String REF = "a reference";
    private final String RESULTDESC = "a result";

    private Standfirst standfirst;
    private Citation citation;
    private KeyResult keyResult;

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

        standfirst = Standfirst.builder().id(1L).rationale(STANDFIRSTrationale).approach(STANDFIRSTapproach).build();
        citation = Citation.builder().id(2L).reference(REF).build();
        keyResult = KeyResult.builder().description(RESULTDESC).id(3L).priority(3).build();

        expositionPieceDTO1.setId(1L);
        expositionPieceDTO1.setKeyword(KEYWORD);
        expositionPieceDTO1.setTitle(TITLE);
        expositionPieceDTO1.setStandfirstDTO(StandfirstMapper.INSTANCE.standfirstToStandfirstDTO(standfirst));
        expositionPieceDTO1.setExpositionPurpose(PURPOSE);
        expositionPieceDTO1.setCurrentProgress(PROGRESS);
        expositionPieceDTO1.setKeyResultDTOList(ExpositionPieceMapper.toKeyResultDTOList(List.of(keyResult)));
        expositionPieceDTO1.setCitationDTOList(ExpositionPieceMapper.toCitationDTOList(List.of(citation)));
        expositionPieceDTO1.setFutureWork(FUTUREWORK);

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