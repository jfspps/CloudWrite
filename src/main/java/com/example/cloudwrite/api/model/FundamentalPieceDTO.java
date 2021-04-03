package com.example.cloudwrite.api.model;

import lombok.Data;

@Data
public class FundamentalPieceDTO {

    private Long id;

    private String title;

    private String keyword;

    private String prerequisites;

    private String summary;

    private ConceptDTOList conceptDTOList;
}
