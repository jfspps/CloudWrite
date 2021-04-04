package com.example.cloudwrite.controller.api;

import com.example.cloudwrite.api.model.FundamentalPieceDTOList;
import com.example.cloudwrite.service.DTO.FundamentalPieceDTOService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

// @RestController does away with returning ResponseEntity<> and is a more modern and cleaner
// implementation cf. @Controller for REST APIs (refactored below)
@RestController
@RequestMapping(FundamentalDTOController.ROOT_URL)
public class FundamentalDTOController {

    private final FundamentalPieceDTOService fundamentalPieceDTOService;

    public static final String ROOT_URL = "/api/fundamentals";

    public FundamentalDTOController(FundamentalPieceDTOService fundamentalPieceDTOService) {
        this.fundamentalPieceDTOService = fundamentalPieceDTOService;
    }

    /**
     * Retrieves all fundamental pieces on file, including concepts
     * @return  JSON formatted list, by default
     */
    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public FundamentalPieceDTOList getAllFunPieces(){
        return new FundamentalPieceDTOList(fundamentalPieceDTOService.findAll().getFundamentalPieceDTOS());
    }

    /**
     * Retrieves all fundamental pieces on file (including concepts) by keyword
     * @param keyword   Search parameter (case insensitive substring of keywords on file)
     * @return  JSON formatted list, by default
     */
    @GetMapping("/{keyword}/search")
    @ResponseStatus(HttpStatus.OK)
    public FundamentalPieceDTOList getFunPiecesByKeyword(@PathVariable("keyword") String keyword){
        return new FundamentalPieceDTOList(fundamentalPieceDTOService.findAllByKeyword(keyword).getFundamentalPieceDTOS());
    }
}
