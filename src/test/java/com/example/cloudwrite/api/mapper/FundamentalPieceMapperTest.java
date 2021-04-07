package com.example.cloudwrite.api.mapper;

import com.example.cloudwrite.JAXBModel.ConceptDTOList;
import com.example.cloudwrite.JAXBModel.FundamentalPieceDTO;
import com.example.cloudwrite.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FundamentalPieceMapperTest {

    private final String TITLE = "a title";
    private final String KEYWORD = "a keyword";
    private final String PREREQ = "a prerequisite";
    private final String SUMMARY = "what's next";

    private final String CONCEPT = "something important";

    private FundamentalPiece fundamentalPiece;
    private Concept concept;

    @BeforeEach
    void setUp() {
        concept = Concept.builder().id(2L).priority(-1).description(CONCEPT).build();
        fundamentalPiece = FundamentalPiece.builder()
                .id(1L)
                .keyword(KEYWORD)
                .summary(SUMMARY)
                .prerequisites(PREREQ)
                .title(TITLE)
                .conceptList(Collections.singletonList(concept))
                .build();
    }

    @Test
    void funPieceToFunPieceDTO() {
        FundamentalPieceDTO fundamentalPieceDTO = FundamentalPieceMapper.INSTANCE.funPieceToFunPieceDTO(fundamentalPiece);

        assertEquals(KEYWORD, fundamentalPieceDTO.getKeyword());
        assertEquals(SUMMARY, fundamentalPieceDTO.getSummary());
        assertEquals(PREREQ, fundamentalPieceDTO.getPrerequisites());
        assertEquals(TITLE, fundamentalPieceDTO.getTitle());
        assertEquals(CONCEPT, fundamentalPieceDTO.getConceptDTOList().getConceptDTOs().get(0).getDescription());
    }

    @Test
    void toConceptDTOList() {
        List<Concept> concepts = new ArrayList<>(List.of(concept));

        ConceptDTOList conceptDTOList = FundamentalPieceMapper.toConceptDTOList(concepts);

        assertEquals(CONCEPT, conceptDTOList.getConceptDTOs().get(0).getDescription());
    }
}