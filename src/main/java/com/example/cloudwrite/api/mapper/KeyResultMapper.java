package com.example.cloudwrite.api.mapper;

import com.example.cloudwrite.api.model.KeyResultDTO;
import com.example.cloudwrite.model.KeyResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

// note that changing the annotation to @Mapper and @Component causes the context to fail on loading
@Mapper(componentModel = "spring")
public interface KeyResultMapper {

    KeyResultMapper INSTANCE = Mappers.getMapper(KeyResultMapper.class);

    KeyResultDTO keyResultToKeyResultDTO(KeyResult keyResult);
}
