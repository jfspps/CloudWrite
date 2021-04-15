package com.example.cloudwrite.controller.api;

import com.example.cloudwrite.JAXBModel.ResearchPieceDTO;
import com.example.cloudwrite.JAXBModel.ResearchPieceDTOList;
import com.example.cloudwrite.api.mapper.ResearchPieceMapper;
import com.example.cloudwrite.api.mapper.StandfirstMapper;
import com.example.cloudwrite.model.Citation;
import com.example.cloudwrite.model.KeyResult;
import com.example.cloudwrite.model.Standfirst;
import com.example.cloudwrite.service.DTO.ResearchPieceDTOService;
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
class ResearchDTOControllerTest {

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
    ResearchPieceDTOService researchPieceDTOService;

    // inject mocks into the controller automatically
    @InjectMocks
    ResearchDTOController researchDTOController;

    MockMvc mockMvc;

    ResearchPieceDTO researchPieceDTO1 = new ResearchPieceDTO();
    ResearchPieceDTO researchPieceDTO2 = new ResearchPieceDTO();
    ResearchPieceDTOList pieceDTOS;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(researchDTOController).build();

        standfirst = Standfirst.builder().id(1L).rationale(STANDFIRSTrationale).approach(STANDFIRSTapproach).build();
        citation = Citation.builder().id(2L).reference(REF).build();
        keyResult = KeyResult.builder().description(RESULTDESC).id(3L).priority(3).build();

        researchPieceDTO1.setId(1L);
        researchPieceDTO1.setKeyword(KEYWORD);
        researchPieceDTO1.setTitle(TITLE);
        researchPieceDTO1.setStandfirstDTO(StandfirstMapper.INSTANCE.standfirstToStandfirstDTO(standfirst));
        researchPieceDTO1.setResearchPurpose(PURPOSE);
        researchPieceDTO1.setCurrentProgress(PROGRESS);
        researchPieceDTO1.setKeyResultDTOList(ResearchPieceMapper.toKeyResultDTOList(List.of(keyResult)));
        researchPieceDTO1.setCitationDTOList(ResearchPieceMapper.toCitationDTOList(List.of(citation)));
        researchPieceDTO1.setFutureWork(FUTUREWORK);

        researchPieceDTO2.setId(2L);
        researchPieceDTO2.setKeyword(KEYWORD + "sss");
        pieceDTOS = new ResearchPieceDTOList();
        pieceDTOS.getResearchPieces().addAll(Arrays.asList(researchPieceDTO1, researchPieceDTO2));
    }

    // these test expect JSON returns instead of XML returns

    @Test
    void getAllExpoPieces() throws Exception {
        when(researchPieceDTOService.findAll()).thenReturn(pieceDTOS);

        mockMvc.perform(get("/api/research/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.researchPieces", hasSize(2))); // $ is root, followed by DTO properties
    }

    @Test
    void getExpoPiecesByKeyword() throws Exception {
        when(researchPieceDTOService.findAllByKeyword(anyString())).thenReturn(pieceDTOS);

        mockMvc.perform(get("/api/research/cocoa/search")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.researchPieces", hasSize(2)));

    }
}