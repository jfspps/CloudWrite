package com.example.cloudwrite.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * A collection of citations as part of an exposition piece
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CitationDTOList {

    @JsonProperty("citations")
    private List<CitationDTO> citationDTOs;
}
