package com.example.cloudwrite.api.mapper;

import com.example.cloudwrite.JAXBModel.CitationDTOList;
import com.example.cloudwrite.JAXBModel.ResearchPieceDTO;
import com.example.cloudwrite.JAXBModel.KeyResultDTOList;
import com.example.cloudwrite.model.Citation;
import com.example.cloudwrite.model.ResearchPiece;
import com.example.cloudwrite.model.KeyResult;
import com.example.cloudwrite.model.Standfirst;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ResearchPieceMapperTest {

    private final String TITLE = "a title";
    private final String KEYWORD = "a keyword";
    private final String PURPOSE = "a purpose";
    private final String PROGRESS = "current progress";
    private final String FUTUREWORK = "what's next";

    private final String STANDFIRSTrationale = "why this is relevant";
    private final String STANDFIRSTapproach = "how to handle it";

    private final String REF = "a reference";
    private final String RESULTDESC = "a result";

    private ResearchPiece researchPiece;
    private Standfirst standfirst;
    private Citation citation;
    private KeyResult keyResult;

    @BeforeEach
    void setUp() {
        standfirst = Standfirst.builder().id(1L).rationale(STANDFIRSTrationale).approach(STANDFIRSTapproach).build();
        citation = Citation.builder().id(2L).reference(REF).build();
        keyResult = KeyResult.builder().description(RESULTDESC).id(3L).priority(3).build();

        researchPiece = ResearchPiece.builder()
                .citations(Collections.singletonList(citation))
                .keyResults(Collections.singletonList(keyResult))
                .standfirst(standfirst)
                .title(TITLE)
                .keyword(KEYWORD)
                .researchPurpose(PURPOSE)
                .currentProgress(PROGRESS)
                .futureWork(FUTUREWORK).build();
    }

    @Test
    void expoPieceToExpoPieceDTO() {
        ResearchPieceDTO researchPieceDTO = ResearchPieceMapper.INSTANCE.resPieceToResPieceDTO(researchPiece);

        assertEquals(TITLE, researchPieceDTO.getTitle());
        assertEquals(KEYWORD, researchPieceDTO.getKeyword());
        assertEquals(PURPOSE, researchPieceDTO.getResearchPurpose());
        assertEquals(PROGRESS, researchPieceDTO.getCurrentProgress());
        assertEquals(FUTUREWORK, researchPieceDTO.getFutureWork());
        assertEquals(STANDFIRSTapproach, researchPieceDTO.getStandfirstDTO().getApproach());
        assertEquals(STANDFIRSTrationale, researchPieceDTO.getStandfirstDTO().getRationale());
        assertEquals(REF, researchPieceDTO.getCitationDTOList().getCitationDTOs().get(0).getReference());
        assertEquals(RESULTDESC, researchPieceDTO.getKeyResultDTOList().getKeyResultDTOs().get(0).getDescription());
        assertEquals(STANDFIRSTapproach, researchPieceDTO.getStandfirstDTO().getApproach());
        assertEquals(3, researchPieceDTO.getKeyResultDTOList().getKeyResultDTOs().get(0).getPriority());
    }

    @Test
    void toKeyResultDTOList() {
        List<KeyResult> results = new ArrayList<>(List.of(keyResult));

        KeyResultDTOList keyResultDTOList = ResearchPieceMapper.toKeyResultDTOList(results);

        assertEquals(RESULTDESC, keyResultDTOList.getKeyResultDTOs().get(0).getDescription());
    }

    @Test
    void toCitationDTOList() {
        List<Citation> citations = new ArrayList<>(List.of(citation));

        CitationDTOList citationDTOList = ResearchPieceMapper.toCitationDTOList(citations);

        assertEquals(REF, citationDTOList.getCitationDTOs().get(0).getReference());
    }
}