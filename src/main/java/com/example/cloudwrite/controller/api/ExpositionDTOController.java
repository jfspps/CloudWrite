package com.example.cloudwrite.controller.api;

import com.example.cloudwrite.api.model.ExpositionPieceDTOList;
import com.example.cloudwrite.service.DTO.ExpositionPieceDTOService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

// @RestController does away with returning ResponseEntity<> and is a more modern and cleaner
// implementation cf. @Controller for REST APIs (refactored below)
// @Controller + @ResponseBody = @RestController (needed for Swagger to recognise ExpositionPieceDTO with http://localhost:5000/swagger-ui.html
@RestController
@ResponseBody
@RequestMapping(ExpositionDTOController.ROOT_URL)
@Tag(name = "exposition-controller", description = "Manages exposition piece CRUD ops")
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
    @Operation(summary = "Lists all exposition pieces")
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
    @Operation(summary = "Lists all exposition pieces with the given keyword", description = "Returns pieces where the provided keyword is a substring of the keyword on file. Case-insensitive.")
    @GetMapping("/{keyword}/search")
    @ResponseStatus(HttpStatus.OK)
    public ExpositionPieceDTOList getExpoPiecesByKeyword(@PathVariable("keyword") String keyword){
        return new ExpositionPieceDTOList(expositionPieceDTOService.findAllByKeyword(keyword).getExpositionPieceDTOS());
    }
}
