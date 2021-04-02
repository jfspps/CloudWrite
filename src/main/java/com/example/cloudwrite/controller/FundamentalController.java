package com.example.cloudwrite.controller;

import com.example.cloudwrite.model.Citation;
import com.example.cloudwrite.model.Concept;
import com.example.cloudwrite.model.ExpositionPiece;
import com.example.cloudwrite.model.FundamentalPiece;
import com.example.cloudwrite.service.ConceptService;
import com.example.cloudwrite.service.FundamentalPieceService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Secured({"ROLE_ADMIN", "ROLE_USER"})
@RequestMapping("/fundamentals")
@Slf4j
public class FundamentalController {

    private final ConceptService conceptService;
    private final FundamentalPieceService fundamentalPieceService;

    //prevent the HTTP form POST from editing listed properties
    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder){
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/{id}")
    public String getFundamental(@PathVariable("id") String ID, Model model) throws NotFoundException {
        if (fundamentalPieceService.findById(Long.valueOf(ID)) == null){
            throw new NotFoundException("Resource not found");
        }

        FundamentalPiece piece = fundamentalPieceService.findById(Long.valueOf(ID));

        List<Concept> conceptList = piece.getConceptList();
        Collections.sort(conceptList);

        model.addAttribute("concepts", conceptList);
        model.addAttribute("fundamental", piece);
        return "/fundamentals/fundamentalDetail";
    }
}
