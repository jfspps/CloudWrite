package com.example.cloudwrite.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.xml.bind.annotation.*;

/**
 * A fundamental piece
 */
@Data
@XmlType(namespace = "http://cloudwrite.com",
        propOrder = {"id", "title", "keyword", "prerequisites", "conceptDTOs", "summary"})
@XmlAccessorType(XmlAccessType.FIELD)
public class FundamentalPieceDTO {

    @XmlTransient
    private Long id;

    @XmlElement(defaultValue = "")
    @Schema(description = "The title of the article/piece")
    private String title;

    @XmlElement(defaultValue = "")
    @Schema(description = "Keyword used to search for articles/pieces on file")
    private String keyword;

    @XmlElement(defaultValue = "")
    @Schema(description = "A summary of the prerequisite knowledge needed")
    private String prerequisites;

    @XmlElement(defaultValue = "")
    @Schema(description = "The closing remarks and summary of the piece/article")
    private String summary;

    @Schema(description = "A list of the concepts covered")
    private ConceptDTOList conceptDTOs;
}
