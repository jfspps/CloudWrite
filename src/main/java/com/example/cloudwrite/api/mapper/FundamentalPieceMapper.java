package com.example.cloudwrite.api.mapper;

import com.example.cloudwrite.api.model.FundamentalPieceDTO;
import com.example.cloudwrite.model.FundamentalPiece;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

// note that changing the annotation to @Mapper and @Component causes the context to fail on loading
@Mapper(componentModel = "spring")
public interface FundamentalPieceMapper {

    FundamentalPieceMapper INSTANCE = Mappers.getMapper(FundamentalPieceMapper.class);

    FundamentalPieceDTO funPieceToFunPieceDTO(FundamentalPiece fundamentalPiece);
}
