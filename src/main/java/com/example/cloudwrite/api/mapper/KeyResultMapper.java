package com.example.cloudwrite.api.mapper;

import com.example.cloudwrite.api.model.KeyResultDTO;
import com.example.cloudwrite.model.KeyResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface KeyResultMapper {

    KeyResultMapper INSTANCE = Mappers.getMapper(KeyResultMapper.class);

    KeyResultDTO keyResultToKeyResultDTO(KeyResult keyResult);
}
