package com.example.cloudwrite.controller.api;

import com.example.cloudwrite.api.model.FundamentalPieceDTOList;
import com.example.cloudwrite.service.DTO.FundamentalPieceDTOService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/fundamentals")
public class FundamentalDTOController {

    private final FundamentalPieceDTOService fundamentalPieceDTOService;

    public FundamentalDTOController(FundamentalPieceDTOService fundamentalPieceDTOService) {
        this.fundamentalPieceDTOService = fundamentalPieceDTOService;
    }

    @GetMapping("/")
    public ResponseEntity<FundamentalPieceDTOList> getAllFunPieces(){
        return new ResponseEntity<>(new FundamentalPieceDTOList(fundamentalPieceDTOService.findAll().getFundamentalPieceDTOS()), HttpStatus.OK);
    }

    @GetMapping("/{keyword}/search")
    public ResponseEntity<FundamentalPieceDTOList> getFunPiecesByKeyword(@PathVariable("keyword") String keyword){
        return new ResponseEntity<>(fundamentalPieceDTOService.findAllByKeyword(keyword), HttpStatus.OK);
    }
}
