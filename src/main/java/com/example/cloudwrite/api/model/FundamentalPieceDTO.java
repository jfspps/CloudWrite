package com.example.cloudwrite.api.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class FundamentalPieceDTO {

    private Long id;

    private String title;

    private String keyword;

    private String prerequisites;

    private String summary;
}
