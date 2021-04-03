package com.example.cloudwrite.api.mapper;

import com.example.cloudwrite.api.model.CitationDTO;
import com.example.cloudwrite.model.Citation;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

// note that changing the annotation to @Mapper and @Component causes the context to fail on loading
@Mapper(componentModel = "spring")public interface CitationMapper {

    CitationMapper INSTANCE = Mappers.getMapper(CitationMapper.class);

    CitationDTO citationToCitationDTO(Citation citation);
}
