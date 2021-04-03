package com.example.cloudwrite.api.mapper;

import com.example.cloudwrite.api.model.CitationDTO;
import com.example.cloudwrite.model.Citation;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface CitationMapper {

    CitationMapper INSTANCE = Mappers.getMapper(CitationMapper.class);

    CitationDTO citationToCitationDTO(Citation citation);
}
