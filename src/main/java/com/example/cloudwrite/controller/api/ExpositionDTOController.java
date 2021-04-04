package com.example.cloudwrite.controller.api;

import com.example.cloudwrite.api.model.ExpositionPieceDTOList;
import com.example.cloudwrite.service.DTO.ExpositionPieceDTOService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

// @RestController does away with returning ResponseEntity<> and is a more modern and cleaner
// implementation cf. @Controller for REST APIs (refactored below)
@RestController
@RequestMapping(ExpositionDTOController.ROOT_URL)
public class ExpositionDTOController {

    private final ExpositionPieceDTOService expositionPieceDTOService;

    public static final String ROOT_URL = "/api/expositions";

    public ExpositionDTOController(ExpositionPieceDTOService expositionPieceDTOService) {
        this.expositionPieceDTOService = expositionPieceDTOService;
    }

    /**
     * Retrieves all exposition pieces on file, including key results and citations
     * @return  JSON formatted list, by default
     */
    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public ExpositionPieceDTOList getAllExpoPieces(){
        return new ExpositionPieceDTOList(expositionPieceDTOService.findAll().getExpositionPieceDTOS());
    }

    /**
     * Retrieves all expositions pieces on file (including key results and citations) by keyword
     * @param keyword   Search parameter (case insensitive substring of keywords on file)
     * @return  JSON formatted list, by default
     */
    @GetMapping("/{keyword}/search")
    @ResponseStatus(HttpStatus.OK)
    public ExpositionPieceDTOList getExpoPiecesByKeyword(@PathVariable("keyword") String keyword){
        return new ExpositionPieceDTOList(expositionPieceDTOService.findAllByKeyword(keyword).getExpositionPieceDTOS());
    }
}
