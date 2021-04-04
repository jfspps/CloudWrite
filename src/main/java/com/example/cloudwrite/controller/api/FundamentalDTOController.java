package com.example.cloudwrite.controller.api;

import com.example.cloudwrite.api.model.FundamentalPieceDTOList;
import com.example.cloudwrite.service.DTO.FundamentalPieceDTOService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

// @RestController does away with returning ResponseEntity<> and is a more modern and cleaner
// implementation cf. @Controller for REST APIs (refactored below)
// @Controller + @ResponseBody = @RestController (needed for Swagger to recognise FundamentalPieceDTO with http://localhost:5000/swagger-ui.html
@RestController
@ResponseBody
@RequestMapping(FundamentalDTOController.ROOT_URL)
@Tag(name = "fundamentals-controller", description = "Manages fundamental piece CRUD ops")
public class FundamentalDTOController {

    private final FundamentalPieceDTOService fundamentalPieceDTOService;

    public static final String ROOT_URL = "/api/fundamentals";

    public FundamentalDTOController(FundamentalPieceDTOService fundamentalPieceDTOService) {
        this.fundamentalPieceDTOService = fundamentalPieceDTOService;
    }

    /**
     * Retrieves all fundamental pieces on file, including concepts
     * @return  XML formatted list, by default
     */
    @Operation(summary = "Lists all fundamental pieces")
    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public FundamentalPieceDTOList getAllFunPieces(){
        return new FundamentalPieceDTOList(fundamentalPieceDTOService.findAll().getFundamentalPieceDTOS());
    }

    /**
     * Retrieves all fundamental pieces on file (including concepts) by keyword
     * @param keyword   Search parameter (case insensitive substring of keywords on file)
     * @return  XML formatted list, by default
     */
    @Operation(summary = "Lists all fundamental pieces with the given keyword", description = "Returns pieces where the provided keyword is a substring of the keyword on file. Case-insensitive.")
    @GetMapping("/{keyword}/search")
    @ResponseStatus(HttpStatus.OK)
    public FundamentalPieceDTOList getFunPiecesByKeyword(@PathVariable("keyword") String keyword){
        return new FundamentalPieceDTOList(fundamentalPieceDTOService.findAllByKeyword(keyword).getFundamentalPieceDTOS());
    }
}
