package com.example.cloudwrite.api.model;


import lombok.Data;

@Data
public class ExpositionPieceDTO {

    private Long id;

    private String title;

    private String keyword;

    private String expositionPurpose;

    private String currentProgress;

    private String futureWork;

    private KeyResultDTOList keyResultDTOList;

    private CitationDTOList citationDTOList;
}
