package com.example.cloudwrite.controller.api;

import com.example.cloudwrite.api.model.ExpositionPieceDTOList;
import com.example.cloudwrite.service.DTO.ExpositionPieceDTOService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/expositions")
public class ExpositionDTOController {

    private final ExpositionPieceDTOService expositionPieceDTOService;

    public ExpositionDTOController(ExpositionPieceDTOService expositionPieceDTOService) {
        this.expositionPieceDTOService = expositionPieceDTOService;
    }

    @GetMapping("/")
    public ResponseEntity<ExpositionPieceDTOList> getAllExpoPieces(){
        // todo: include all related fields for Exposition pieces
        return new ResponseEntity<>(new ExpositionPieceDTOList(expositionPieceDTOService.findAll().getExpositionPieceDTOS()), HttpStatus.OK);
    }

    @GetMapping("/{keyword}/search")
    public ResponseEntity<ExpositionPieceDTOList> getExpoPiecesByKeyword(@PathVariable("keyword") String keyword){
        // todo: include all related fields for Exposition pieces
        return new ResponseEntity<>(expositionPieceDTOService.findAllByKeyword(keyword), HttpStatus.OK);
    }
}
