package com.example.cloudwrite.api.mapper;

import com.example.cloudwrite.JAXBModel.StandfirstDTO;
import com.example.cloudwrite.model.Standfirst;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

// see application.properties
@Mapper
public interface StandfirstMapper {

    StandfirstMapper INSTANCE = Mappers.getMapper(StandfirstMapper.class);

    StandfirstDTO standfirstToStandfirstDTO(Standfirst standfirst);
}
