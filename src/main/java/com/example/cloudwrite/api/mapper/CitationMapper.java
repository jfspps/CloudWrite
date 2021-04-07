package com.example.cloudwrite.api.mapper;

import com.example.cloudwrite.JAXBModel.CitationDTO;
import com.example.cloudwrite.model.Citation;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

// see application.properties
@Mapper
public interface CitationMapper {

    CitationMapper INSTANCE = Mappers.getMapper(CitationMapper.class);

    CitationDTO citationToCitationDTO(Citation citation);
}
