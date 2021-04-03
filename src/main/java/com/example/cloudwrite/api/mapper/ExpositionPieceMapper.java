package com.example.cloudwrite.api.mapper;

import com.example.cloudwrite.api.model.ExpositionPieceDTO;
import com.example.cloudwrite.model.ExpositionPiece;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ExpositionPieceMapper {

    ExpositionPieceMapper INSTANCE = Mappers.getMapper(ExpositionPieceMapper.class);

    ExpositionPieceDTO expoPieceToExpoPieceDTO(ExpositionPiece expositionPiece);
}
