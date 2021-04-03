package com.example.cloudwrite.api.mapper;

import com.example.cloudwrite.api.model.FundamentalPieceDTO;
import com.example.cloudwrite.model.FundamentalPiece;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface FundamentalPieceMapper {

    FundamentalPieceMapper INSTANCE = Mappers.getMapper(FundamentalPieceMapper.class);

    FundamentalPieceDTO funPieceToFunPieceDTO(FundamentalPiece fundamentalPiece);
}
