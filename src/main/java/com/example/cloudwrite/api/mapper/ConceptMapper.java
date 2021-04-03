package com.example.cloudwrite.api.mapper;

import com.example.cloudwrite.api.model.ConceptDTO;
import com.example.cloudwrite.model.Concept;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

// note that changing the annotation to @Mapper and @Component causes the context to fail on loading
@Mapper(componentModel = "spring")
public interface ConceptMapper {

    ConceptMapper INSTANCE = Mappers.getMapper(ConceptMapper.class);

    ConceptDTO conceptToConceptDTO(Concept concept);
}
