package com.example.cloudwrite.api.model;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.xml.bind.annotation.*;

/**
 * An exposition of a research
 */
@Data
@XmlType(namespace = "http://cloudwrite.com/expoPiece",
        propOrder = {"id", "title", "keyword", "standfirstDTO", "expositionPurpose", "currentProgress", "keyResultDTOs", "futureWork", "citationDTOs"})
@XmlAccessorType(XmlAccessType.FIELD)
public class ExpositionPieceDTO {

    @XmlTransient
    private Long id;

    @XmlElement(defaultValue = "")
    @Schema(description = "The title of the article/piece")
    private String title;

    @XmlElement(defaultValue = "")
    @Schema(description = "Keyword used to search for articles/pieces on file")
    private String keyword;

    @Schema(description = "The standfirst or headline of the article/piece")
    private StandfirstDTO standfirstDTO;

    @XmlElement(defaultValue = "")
    @Schema(description = "A summary of the top-level areas/issues that the research attempts to address")
    private String expositionPurpose;

    @XmlElement(defaultValue = "")
    @Schema(description = "A summary of the current research efforts conducted")
    private String currentProgress;

    @Schema(description = "A list of key results")
    private KeyResultDTOList keyResultDTOs;

    @XmlElement(defaultValue = "")
    @Schema(description = "A summary of future work which follows the research presented")
    private String futureWork;

    @Schema(description = "A list of references/citations relevant to the article/piece")
    private CitationDTOList citationDTOs;
}
