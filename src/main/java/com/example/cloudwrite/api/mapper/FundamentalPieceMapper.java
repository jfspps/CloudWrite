package com.example.cloudwrite.api.mapper;

import com.example.cloudwrite.JAXBModel.ConceptDTOList;
import com.example.cloudwrite.JAXBModel.FundamentalPieceDTO;
import com.example.cloudwrite.model.Concept;
import com.example.cloudwrite.model.FundamentalPiece;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

// // see application.properties
@Mapper
public interface FundamentalPieceMapper {

    FundamentalPieceMapper INSTANCE = Mappers.getMapper(FundamentalPieceMapper.class);

    @Mapping(source = "conceptList", target = "conceptDTOList", qualifiedBy = ConceptListMapper.class)
    FundamentalPieceDTO funPieceToFunPieceDTO(FundamentalPiece fundamentalPiece);

    /**
     * Converts a List of Concepts (DB) to a wrapped ConceptDTOList (JAXB or other DTO)
     */
    @ConceptListMapper
    static ConceptDTOList toConceptDTOList(List<Concept> concepts){
        ConceptDTOList conceptDTOList = new ConceptDTOList();

        concepts.forEach(concept -> {
            conceptDTOList.getConceptDTOs()
                    .add(ConceptMapper.INSTANCE.conceptToConceptDTO(concept));
        });

        return conceptDTOList;
    }
}
