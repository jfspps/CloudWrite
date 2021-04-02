package com.example.cloudwrite.api.mapper;

import com.example.cloudwrite.api.model.StandfirstDTO;
import com.example.cloudwrite.model.Standfirst;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface StandfirstMapper {

    StandfirstMapper INSTANCE = Mappers.getMapper(StandfirstMapper.class);

    StandfirstDTO standfirstToStandfirstDTO(Standfirst standfirst);
}
