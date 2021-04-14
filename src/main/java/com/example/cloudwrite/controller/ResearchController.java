package com.example.cloudwrite.controller;

import com.example.cloudwrite.model.*;
import com.example.cloudwrite.service.*;
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
import java.util.*;

@Controller
@RequiredArgsConstructor
@Secured({"ROLE_ADMIN", "ROLE_USER"})
@RequestMapping("/research")
@Slf4j
public class ResearchController {

    private final ResearchPieceService researchPieceService;
    private final KeyResultService keyResultService;
    private final StandfirstService standfirstService;
    private final CitationService citationService;

    //prevent the HTTP form POST from editing listed properties
    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder){
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/new")
    public String getNewResearch(Model model){
        ResearchPiece piece = ResearchPiece.builder().build();

        model.addAttribute("research", piece);
        return "/research/newResearch";
    }

    @PostMapping("/new")
    public String postNewResearch(@ModelAttribute("research") ResearchPiece newResearch){
        Standfirst savedStandfirst = standfirstService.save(newResearch.getStandfirst());
        newResearch.setStandfirst(savedStandfirst);

        ResearchPiece savedPiece = researchPieceService.save(newResearch);
        savedStandfirst.setResearchPiece(savedPiece);

        return "redirect:/research/" + researchPieceService.save(savedPiece).getId();
    }

    @GetMapping("/{id}")
    public String getResearch(@PathVariable("id") String ID, Model model) throws NotFoundException {
        if (researchPieceService.findById(Long.valueOf(ID)) == null){
            throw new NotFoundException("Resource not found");
        }

        ResearchPiece piece = researchPieceService.findById(Long.valueOf(ID));

        // sort by priority (sort returns void)
        Collections.sort(piece.getKeyResults());
        model.addAttribute("results", piece.getKeyResults());

        List<Citation> citations = piece.getCitations();
        Collections.sort(citations);

        model.addAttribute("references", citations);
        model.addAttribute("research", piece);
        return "/research/researchDetail";
    }

    @PostMapping("/{id}/newResult")
    public String postNewResult(@PathVariable("id") String ID) throws NotFoundException {
        if (researchPieceService.findById(Long.valueOf(ID)) == null){
            throw new NotFoundException("Resource not found");
        }

        ResearchPiece piece = researchPieceService.findById(Long.valueOf(ID));

        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        KeyResult newResult = KeyResult.builder().description("New result created " + simpleDateFormat.format(timestamp)).build();
        KeyResult savedResult = keyResultService.save(newResult);
        piece.getKeyResults().add(savedResult);

        ResearchPiece savedPiece = researchPieceService.save(piece);
        savedResult.setResearchPiece(savedPiece);
        keyResultService.save(savedResult);

        return "redirect:/research/" + savedPiece.getId();
    }

    @PostMapping("/{id}/newReference")
    public String postNewReference(@PathVariable("id") String ID) throws NotFoundException {
        if (researchPieceService.findById(Long.valueOf(ID)) == null){
            throw new NotFoundException("Resource not found");
        }

        ResearchPiece piece = researchPieceService.findById(Long.valueOf(ID));

        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Citation newCitation = Citation.builder().reference("New reference created " + simpleDateFormat.format(timestamp)).build();
        Citation savedCitation = citationService.save(newCitation);
        piece.getCitations().add(savedCitation);

        ResearchPiece savedPiece = researchPieceService.save(piece);
        savedCitation.setPiece(savedPiece);
        citationService.save(savedCitation);

        return "redirect:/research/" + savedPiece.getId();
    }

    @PostMapping("/{id}/update")
    public String postUpdateResearch(@ModelAttribute("research") ResearchPiece piece, @PathVariable("id") String ID) throws NotFoundException{
        if (researchPieceService.findById(Long.valueOf(ID)) == null){
            throw new NotFoundException("Resource not found");
        }

        ResearchPiece pieceOnFile = researchPieceService.findById(Long.valueOf(ID));

        //allow users to clear field entries by submitting empty string fields
        pieceOnFile.setTitle(piece.getTitle());
        pieceOnFile.setKeyword(piece.getKeyword());
        pieceOnFile.setCurrentProgress(piece.getCurrentProgress());

        Standfirst standfirstOnFile = standfirstService.findById(pieceOnFile.getStandfirst().getId());
        Standfirst standfirstSubmitted = piece.getStandfirst();
        standfirstOnFile.setApproach(standfirstSubmitted.getApproach());
        standfirstOnFile.setRationale(standfirstSubmitted.getRationale());
        Standfirst updatedStandfirst = standfirstService.save(standfirstOnFile);
        pieceOnFile.setStandfirst(updatedStandfirst);

        pieceOnFile.setResearchPurpose(piece.getResearchPurpose());
        pieceOnFile.setFutureWork(piece.getFutureWork());

        ResearchPiece updated = researchPieceService.save(pieceOnFile);

        return "redirect:/research/" + updated.getId();
    }

    @PostMapping("/{resId}/updateResults")
    public String postUpdateResearchResult(@PathVariable("resId")String researchID,
                                           @RequestParam("deletable")String[] results,
                                           @RequestParam("priority")String[] priorities,
                                           @RequestParam("description")String[] descriptions) throws NotFoundException{
        if (researchPieceService.findById(Long.valueOf(researchID)) == null){
            throw new NotFoundException("Resource not found");
        }

        ResearchPiece pieceOnFile = researchPieceService.findById(Long.valueOf(researchID));
        List<KeyResult> resultsOnFile = pieceOnFile.getKeyResults();
        List<String> descriptionsOnFile = new ArrayList<>();
        List<Integer> prioritiesOnFile = new ArrayList<>();

        readFromResultsOnFile(resultsOnFile, descriptionsOnFile, prioritiesOnFile);

        updatePriorities(priorities, prioritiesOnFile);
        updateDescriptions(descriptions, descriptionsOnFile);

        writeToResultsOnFile(resultsOnFile, descriptionsOnFile, prioritiesOnFile);

        // update fields first before deleting those marked, to keep things in sync
        if (results.length != 0){
            performDeleteResults(results, resultsOnFile);
        }

        ResearchPiece toFile = researchPieceService.save(pieceOnFile);
        log.debug("Research piece key results updated");

        return "redirect:/research/" + toFile.getId();
    }

    @PostMapping("/{researchId}/updateReferences")
    public String postUpdateResearchReferences(
            @PathVariable("researchId")String researchID,
            @RequestParam("ref")String[] references,
            @RequestParam("deletable")String[] toDelete) throws NotFoundException{
        if (researchPieceService.findById(Long.valueOf(researchID)) == null){
            throw new NotFoundException("Resource not found");
        }

        ResearchPiece pieceOnFile = researchPieceService.findById(Long.valueOf(researchID));
        List<Citation> citationsOnFile = pieceOnFile.getCitations();

        for (int i = 0; i < references.length; i++){
            citationsOnFile.get(i).setReference(references[i]);
        }

        if (toDelete.length != 0){
            performDeleteReferences(references, toDelete, citationsOnFile);
        }

        ResearchPiece toFile = researchPieceService.save(pieceOnFile);
        log.debug("Research piece citations updated");

        return "redirect:/research/" + toFile.getId();
    }

    @GetMapping("/{id}/delete")
    public String getDeleteResearch(@PathVariable("id") String ID, Model model) throws NotFoundException {
        if (researchPieceService.findById(Long.valueOf(ID)) == null){
            throw new NotFoundException("Resource not found");
        }

        ResearchPiece piece = researchPieceService.findById(Long.valueOf(ID));

        model.addAttribute("research", piece);

        return "/research/confirmDelete";
    }

    @PostMapping("/{id}/delete")
    public String postDeleteResearch(@PathVariable("id") String ID) throws NotFoundException {
        if (researchPieceService.findById(Long.valueOf(ID)) == null){
            throw new NotFoundException("Resource not found");
        }

        ResearchPiece pieceOnFile = researchPieceService.findById(Long.valueOf(ID));

        // remove all assoc. KeyResults
        pieceOnFile.getKeyResults().forEach(keyResult -> {
            keyResult.setResearchPiece(null);
            keyResultService.delete(keyResult);
        });

        // remove all assoc. citations
        pieceOnFile.getCitations().forEach(citation -> {
            citation.setPiece(null);
            citationService.delete(citation);
        });

        // remove standfirst
        Standfirst standfirstOnFile = pieceOnFile.getStandfirst();
        standfirstOnFile.setResearchPiece(null);
        pieceOnFile.setStandfirst(null);
        standfirstService.delete(standfirstOnFile);

        researchPieceService.delete(pieceOnFile);

        return "redirect:/authenticated";
    }

    private void performDeleteReferences(String[] references, String[] toDelete, List<Citation> citationsOnFile) {
        // mark checked references for deletion
        int pairsProcessed = 0;
        for (int i = 0; i < references.length; i++, pairsProcessed++){
            if (toDelete[i].equals("on")){
                citationsOnFile.get(pairsProcessed).setDeletable(true);
                i++;
            }
        }

        // then delete
        for (int i = citationsOnFile.size() - 1; i >= 0; i--){
            Citation toBeDeleted;
            if (citationsOnFile.get(i).isDeletable()){
                toBeDeleted = citationsOnFile.get(i);
                citationsOnFile.remove(toBeDeleted);
                citationService.delete(toBeDeleted);
            }
        }
    }

    private void writeToResultsOnFile(List<KeyResult> resultsOnFile, List<String> descriptionsOnFile, List<Integer> prioritiesOnFile) {
        // write to resultsOnFile
        int i = 0;
        for (KeyResult result : resultsOnFile) {
            result.setPriority(prioritiesOnFile.get(i));
            result.setDescription(descriptionsOnFile.get(i));
            i++;
        }
    }

    private void readFromResultsOnFile(List<KeyResult> resultsOnFile, List<String> descriptionsOnFile, List<Integer> prioritiesOnFile) {
        // read from resultOnFile
        resultsOnFile.forEach(keyResult -> {
            prioritiesOnFile.add(keyResult.getPriority());
            descriptionsOnFile.add(keyResult.getDescription());
        });
    }

    private void updateDescriptions(String[] descriptions, List<String> descriptionsOnFile) {
//        log.debug("Descriptions received: " + descriptions.length);

        for (int i = 0; i < descriptions.length; i++){
//            log.debug("Description " + i + ": " + descriptions[i]);
//            log.debug("Description on file" + i + ": " + descriptionsOnFile.get(i));
            descriptionsOnFile.set(i, descriptions[i]);
        }
    }

    private void updatePriorities(String[] priorities, List<Integer> prioritiesOnFile) {
//        log.debug("Priorities received: " + priorities.length);

        for (int i = 0; i < priorities.length; i++){
//            log.debug("Priority " + i + ": " + priorities[i]);
//            log.debug("Priorities on file" + i + ": " + prioritiesOnFile.get(i));
            prioritiesOnFile.set(i, Integer.valueOf(priorities[i]));
        }
    }

    private void performDeleteResults(String[] results, List<KeyResult> resultsOnFile) {
        // note that there only as many keyResults as there are deletable elements, so iterate through each
        // the order of each result (in pairs) matches the sorted order in pieceOnFile
        // mark for deletion first
        int pairsProcessed = 0;
        for (int i = 0; i < results.length; i++, pairsProcessed++){
            if (results[i].equals("on")){
//                log.debug("Element " + pairsProcessed + " to be deleted");
                resultsOnFile.get(pairsProcessed).setDeletable(true);
                i++;
            }
        }

        // remove marked results (account for reshuffling of nodes in List, process backwards)
        for (int i = resultsOnFile.size() - 1; i >= 0; i--){
            KeyResult toBeDeleted;
            if (resultsOnFile.get(i).isDeletable()){
                toBeDeleted = resultsOnFile.get(i);
                resultsOnFile.remove(toBeDeleted);
                keyResultService.delete(toBeDeleted);
            }
        }
    }

}
