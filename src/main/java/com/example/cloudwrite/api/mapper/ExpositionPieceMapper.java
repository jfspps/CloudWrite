package com.example.cloudwrite.api.mapper;

import com.example.cloudwrite.api.model.ExpositionPieceDTO;
import com.example.cloudwrite.model.ExpositionPiece;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

// note that changing the annotation to @Mapper and @Component causes the context to fail on loading
@Mapper(componentModel = "spring")
public interface ExpositionPieceMapper {

    ExpositionPieceMapper INSTANCE = Mappers.getMapper(ExpositionPieceMapper.class);

    ExpositionPieceDTO expoPieceToExpoPieceDTO(ExpositionPiece expositionPiece);
}
