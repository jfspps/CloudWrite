package com.example.cloudwrite.api.mapper;

import com.example.cloudwrite.JAXBModel.ConceptDTO;
import com.example.cloudwrite.model.Concept;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

// see application.properties
@Mapper
public interface ConceptMapper {

    ConceptMapper INSTANCE = Mappers.getMapper(ConceptMapper.class);

    ConceptDTO conceptToConceptDTO(Concept concept);
}
