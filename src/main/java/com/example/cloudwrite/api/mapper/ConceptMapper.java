package com.example.cloudwrite.api.mapper;

import com.example.cloudwrite.api.model.ConceptDTO;
import com.example.cloudwrite.model.Concept;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ConceptMapper {

    ConceptMapper INSTANCE = Mappers.getMapper(ConceptMapper.class);

    ConceptDTO conceptToConceptDTO(Concept concept);
}
