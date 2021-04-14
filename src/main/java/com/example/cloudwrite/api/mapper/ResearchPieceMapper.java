package com.example.cloudwrite.api.mapper;

import com.example.cloudwrite.JAXBModel.CitationDTOList;
import com.example.cloudwrite.JAXBModel.ResearchPieceDTO;
import com.example.cloudwrite.JAXBModel.KeyResultDTOList;
import com.example.cloudwrite.model.Citation;
import com.example.cloudwrite.model.ResearchPiece;
import com.example.cloudwrite.model.KeyResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

// see application.properties
@Mapper
public interface ResearchPieceMapper {

    ResearchPieceMapper INSTANCE = Mappers.getMapper(ResearchPieceMapper.class);

    @Mapping(source = "citations", target = "citationDTOList", qualifiedBy = CitationListMapper.class)
    @Mapping(source = "keyResults", target = "keyResultDTOList", qualifiedBy = KeyResultListMapper.class)
    @Mapping(target = "standfirstDTO", source = "standfirst")
    ResearchPieceDTO resPieceToResPieceDTO(ResearchPiece researchPiece);

    /**
     * Converts a List of KeyResults (DB) to a wrapped KeyResultDTOList (JAXB or other DTO)
     */
    @KeyResultListMapper
    static KeyResultDTOList toKeyResultDTOList(List<KeyResult> keyResults){
        KeyResultDTOList keyResultDTOList = new KeyResultDTOList();

        keyResults.forEach(keyResult -> {
            keyResultDTOList.getKeyResultDTOs()
                    .add(KeyResultMapper.INSTANCE.keyResultToKeyResultDTO(keyResult));
        });

        return keyResultDTOList;
    }

    /**
     * Converts a List of Citations (DB) to a wrapped CitationDTOList (JAXB or other DTO)
     */
    @CitationListMapper
    static CitationDTOList toCitationDTOList(List<Citation> citations){
        CitationDTOList citationDTOList = new CitationDTOList();

        citations.forEach(citation -> {
            citationDTOList.getCitationDTOs()
                    .add(CitationMapper.INSTANCE.citationToCitationDTO(citation));
        });

        return citationDTOList;
    }
}
