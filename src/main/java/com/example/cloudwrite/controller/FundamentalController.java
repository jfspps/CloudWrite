package com.example.cloudwrite.controller;

import com.example.cloudwrite.model.*;
import com.example.cloudwrite.service.ConceptService;
import com.example.cloudwrite.service.FundamentalPieceService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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

    @PostMapping("/{id}/update")
    public String postUpdateFundamental(@ModelAttribute("fundamental") FundamentalPiece piece, @PathVariable("id") String ID) throws NotFoundException{
        if (fundamentalPieceService.findById(Long.valueOf(ID)) == null){
            throw new NotFoundException("Resource not found");
        }

        FundamentalPiece pieceOnFile = fundamentalPieceService.findById(Long.valueOf(ID));

        //allow users to clear field entries by submitting empty string fields
        pieceOnFile.setTitle(piece.getTitle());
        pieceOnFile.setKeyword(piece.getKeyword());
        pieceOnFile.setPrerequisites(piece.getPrerequisites());
        pieceOnFile.setSummary(piece.getSummary());

        FundamentalPiece updated = fundamentalPieceService.save(pieceOnFile);

        return "redirect:/fundamentals/" + updated.getId();
    }

    @PostMapping("/{id}/newConcept")
    public String postNewConcept(@PathVariable("id") String ID) throws NotFoundException {
        if (fundamentalPieceService.findById(Long.valueOf(ID)) == null){
            throw new NotFoundException("Resource not found");
        }

        FundamentalPiece piece = fundamentalPieceService.findById(Long.valueOf(ID));

        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Concept newConcept = Concept.builder()
                .description("New concept description created " + simpleDateFormat.format(timestamp))
                .purpose("New concept description created")
                .build();
        Concept savedConcept = conceptService.save(newConcept);
        piece.getConceptList().add(savedConcept);

        FundamentalPiece savedPiece = fundamentalPieceService.save(piece);
        savedConcept.setFundamentalPiece(savedPiece);
        conceptService.save(savedConcept);

        return "redirect:/fundamentals/" + savedPiece.getId();
    }

    @PostMapping("/{Id}/updateConcepts")
    public String postUpdateFundamentalConcepts(@PathVariable("Id")String expoID,
                                             @RequestParam("deletable")String[] concepts,
                                             @RequestParam("priority")String[] priorities,
                                             @RequestParam("description")String[] descriptions,
                                             @RequestParam("purpose")String[] purposes) throws NotFoundException{
        if (fundamentalPieceService.findById(Long.valueOf(expoID)) == null){
            throw new NotFoundException("Resource not found");
        }

        FundamentalPiece pieceOnFile = fundamentalPieceService.findById(Long.valueOf(expoID));
        List<Concept> conceptsOnFile = pieceOnFile.getConceptList();
        List<String> descriptionsOnFile = new ArrayList<>();
        List<String> purposesOnFile = new ArrayList<>();
        List<Integer> prioritiesOnFile = new ArrayList<>();

        readFromConceptsOnFile(conceptsOnFile, descriptionsOnFile, purposesOnFile, prioritiesOnFile);

        updatePriorities(priorities, prioritiesOnFile);
        updatePurposes(purposes, purposesOnFile);
        updateDescriptions(descriptions, descriptionsOnFile);

        writeToConceptsOnFile(conceptsOnFile, descriptionsOnFile, purposesOnFile, prioritiesOnFile);

        if (concepts.length != 0){
            performDeleteResults(concepts, conceptsOnFile);
        }

        FundamentalPiece toFile = fundamentalPieceService.save(pieceOnFile);
        log.debug("Fundamental piece concepts updated");

        return "redirect:/fundamentals/" + toFile.getId();
    }

    private void writeToConceptsOnFile(List<Concept> conceptsOnFile, List<String> descriptionsOnFile, List<String> purposesOnFile, List<Integer> prioritiesOnFile) {
        int i = 0;
        for (Concept concept : conceptsOnFile) {
            concept.setPriority(prioritiesOnFile.get(i));
            concept.setDescription(descriptionsOnFile.get(i));
            concept.setPurpose(purposesOnFile.get(i));
            i++;
        }
    }

    private void readFromConceptsOnFile(List<Concept> conceptsOnFile, List<String> descriptionsOnFile, List<String> purposesOnFile, List<Integer> prioritiesOnFile) {
        conceptsOnFile.forEach(concept -> {
            prioritiesOnFile.add(concept.getPriority());
            purposesOnFile.add(concept.getPurpose());
            descriptionsOnFile.add(concept.getDescription());
        });
    }

    private void updateDescriptions(String[] descriptions, List<String> descriptionsOnFile) {
        for (int i = 0; i < descriptions.length; i++){
            descriptionsOnFile.set(i, descriptions[i]);
        }
    }

    private void updatePurposes(String[] purposes, List<String> purposesOnFile) {
        for (int i = 0; i < purposes.length; i++){
            purposesOnFile.set(i, purposes[i]);
        }
    }
    private void updatePriorities(String[] priorities, List<Integer> prioritiesOnFile) {
        for (int i = 0; i < priorities.length; i++){
            prioritiesOnFile.set(i, Integer.valueOf(priorities[i]));
        }
    }

    private void performDeleteResults(String[] concepts, List<Concept> conceptsOnFile) {
        int pairsProcessed = 0;
        for (int i = 0; i < concepts.length; i++, pairsProcessed++){
            if (concepts[i].equals("on")){
                conceptsOnFile.get(pairsProcessed).setDeletable(true);
                i++;
            }
        }

        for (int i = conceptsOnFile.size() - 1; i >= 0; i--){
            Concept toBeDeleted;
            if (conceptsOnFile.get(i).isDeletable()){
                toBeDeleted = conceptsOnFile.get(i);
                conceptsOnFile.remove(toBeDeleted);
                conceptService.delete(toBeDeleted);
            }
        }
    }
}
