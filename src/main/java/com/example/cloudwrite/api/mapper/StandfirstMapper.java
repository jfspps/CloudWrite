package com.example.cloudwrite.api.mapper;

import com.example.cloudwrite.api.model.StandfirstDTO;
import com.example.cloudwrite.model.Standfirst;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

// note that changing the annotation to @Mapper and @Component causes the context to fail on loading
@Mapper(componentModel = "spring")
public interface StandfirstMapper {

    StandfirstMapper INSTANCE = Mappers.getMapper(StandfirstMapper.class);

    StandfirstDTO standfirstToStandfirstDTO(Standfirst standfirst);
}
