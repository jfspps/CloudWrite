package com.example.cloudwrite.api.mapper;

import com.example.cloudwrite.JAXBModel.KeyResultDTO;
import com.example.cloudwrite.model.KeyResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

// see application.properties
@Mapper
public interface KeyResultMapper {

    KeyResultMapper INSTANCE = Mappers.getMapper(KeyResultMapper.class);

    KeyResultDTO keyResultToKeyResultDTO(KeyResult keyResult);
}
