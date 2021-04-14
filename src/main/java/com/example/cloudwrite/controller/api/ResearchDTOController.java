package com.example.cloudwrite.controller.api;

import com.example.cloudwrite.JAXBModel.ResearchPieceDTOList;
import com.example.cloudwrite.service.DTO.ResearchPieceDTOService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

// @RestController does away with returning ResponseEntity<> and is a more modern and cleaner
// implementation cf. @Controller for REST APIs (refactored below)
// @Controller + @ResponseBody = @RestController (needed for Swagger to recognise Research with http://localhost:5000/swagger-ui.html
@RestController
@ResponseBody
@RequestMapping(ResearchDTOController.ROOT_URL)
@Tag(name = "research-controller", description = "Manages research piece CRUD ops")
public class ResearchDTOController {

    private final ResearchPieceDTOService researchPieceDTOService;

    public static final String ROOT_URL = "/api/research";

    public ResearchDTOController(ResearchPieceDTOService researchPieceDTOService) {
        this.researchPieceDTOService = researchPieceDTOService;
    }

    /**
     * Retrieves all research pieces on file, including key results and citations
     * @return  XML formatted list, by default
     */
    @Operation(summary = "Lists all research pieces")
    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public ResearchPieceDTOList getAllResPieces(){
        ResearchPieceDTOList list = new ResearchPieceDTOList();
        list.getResearchPiece().addAll(researchPieceDTOService.findAll().getResearchPiece());

        return list;
    }

    /**
     * Retrieves all research pieces on file (including key results and citations) by keyword
     * @param keyword   Search parameter (case insensitive substring of keywords on file)
     * @return  XML formatted list, by default
     */
    @Operation(summary = "Lists all research pieces with the given keyword", description = "Returns pieces where the provided keyword is a substring of the keyword on file. Case-insensitive.")
    @GetMapping("/{keyword}/search")
    @ResponseStatus(HttpStatus.OK)
    public ResearchPieceDTOList getResPiecesByKeyword(@PathVariable("keyword") String keyword){
        ResearchPieceDTOList list = new ResearchPieceDTOList();
        list.getResearchPiece().addAll(researchPieceDTOService.findAllByKeyword(keyword).getResearchPiece());

        return list;
    }
}
