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
@RequestMapping("/expositions")
@Slf4j
public class ExpositionController {

    private final ExpositionPieceService expositionPieceService;
    private final KeyResultService keyResultService;
    private final StandfirstService standfirstService;
    private final CitationService citationService;

    //prevent the HTTP form POST from editing listed properties
    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder){
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/new")
    public String getNewExposition(Model model){
        ExpositionPiece piece = ExpositionPiece.builder().build();

        model.addAttribute("exposition", piece);
        return "/expositions/newExpo";
    }

    @PostMapping("/new")
    public String postNewExposition(@ModelAttribute("exposition") ExpositionPiece newExpo){
        Standfirst savedStandfirst = standfirstService.save(newExpo.getStandfirst());
        newExpo.setStandfirst(savedStandfirst);

        ExpositionPiece savedPiece = expositionPieceService.save(newExpo);
        savedStandfirst.setExpositionPiece(savedPiece);

        return "redirect:/expositions/" + expositionPieceService.save(savedPiece).getId();
    }

    @GetMapping("/{id}")
    public String getExposition(@PathVariable("id") String ID, Model model) throws NotFoundException {
        if (expositionPieceService.findById(Long.valueOf(ID)) == null){
            throw new NotFoundException("Resource not found");
        }

        ExpositionPiece piece = expositionPieceService.findById(Long.valueOf(ID));

        // sort by priority (sort returns void)
        Collections.sort(piece.getKeyResults());
        model.addAttribute("results", piece.getKeyResults());

        List<Citation> citations = piece.getCitations();
        Collections.sort(citations);

        model.addAttribute("references", citations);
        model.addAttribute("exposition", piece);
        return "/expositions/expoDetail";
    }

    @PostMapping("/{id}/newResult")
    public String postNewResult(@PathVariable("id") String ID) throws NotFoundException {
        if (expositionPieceService.findById(Long.valueOf(ID)) == null){
            throw new NotFoundException("Resource not found");
        }

        ExpositionPiece piece = expositionPieceService.findById(Long.valueOf(ID));

        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        KeyResult newResult = KeyResult.builder().description("New result created " + simpleDateFormat.format(timestamp)).build();
        KeyResult savedResult = keyResultService.save(newResult);
        piece.getKeyResults().add(savedResult);

        ExpositionPiece savedPiece = expositionPieceService.save(piece);
        savedResult.setExpositionPiece(savedPiece);
        keyResultService.save(savedResult);

        return "redirect:/expositions/" + savedPiece.getId();
    }

    @PostMapping("/{id}/newReference")
    public String postNewReference(@PathVariable("id") String ID) throws NotFoundException {
        if (expositionPieceService.findById(Long.valueOf(ID)) == null){
            throw new NotFoundException("Resource not found");
        }

        ExpositionPiece piece = expositionPieceService.findById(Long.valueOf(ID));

        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Citation newCitation = Citation.builder().ref("New reference created " + simpleDateFormat.format(timestamp)).build();
        Citation savedCitation = citationService.save(newCitation);
        piece.getCitations().add(savedCitation);

        ExpositionPiece savedPiece = expositionPieceService.save(piece);
        savedCitation.setPiece(savedPiece);
        citationService.save(savedCitation);

        return "redirect:/expositions/" + savedPiece.getId();
    }

    @PostMapping("/{id}/update")
    public String postUpdateExposition(@ModelAttribute("exposition") ExpositionPiece piece, @PathVariable("id") String ID) throws NotFoundException{
        if (expositionPieceService.findById(Long.valueOf(ID)) == null){
            throw new NotFoundException("Resource not found");
        }

        ExpositionPiece pieceOnFile = expositionPieceService.findById(Long.valueOf(ID));

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

        pieceOnFile.setExpositionPurpose(piece.getExpositionPurpose());
        pieceOnFile.setFutureWork(piece.getFutureWork());

        ExpositionPiece updated = expositionPieceService.save(pieceOnFile);

        return "redirect:/expositions/" + updated.getId();
    }

    @PostMapping("/{expoId}/updateResults")
    public String postUpdateExpositionResult(@PathVariable("expoId")String expoID,
                                             @RequestParam("deletable")String[] results,
                                             @RequestParam("priority")String[] priorities,
                                             @RequestParam("description")String[] descriptions) throws NotFoundException{
        if (expositionPieceService.findById(Long.valueOf(expoID)) == null){
            throw new NotFoundException("Resource not found");
        }

        ExpositionPiece pieceOnFile = expositionPieceService.findById(Long.valueOf(expoID));
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

        ExpositionPiece toFile = expositionPieceService.save(pieceOnFile);
        log.debug("Exposition piece key results updated");

        return "redirect:/expositions/" + toFile.getId();
    }

    @PostMapping("/{expoId}/updateReferences")
    public String postUpdateExpositionReferences(
            @PathVariable("expoId")String expoID,
            @RequestParam("ref")String[] references,
            @RequestParam("deletable")String[] toDelete) throws NotFoundException{
        if (expositionPieceService.findById(Long.valueOf(expoID)) == null){
            throw new NotFoundException("Resource not found");
        }

        ExpositionPiece pieceOnFile = expositionPieceService.findById(Long.valueOf(expoID));
        List<Citation> citationsOnFile = pieceOnFile.getCitations();

        for (int i = 0; i < references.length; i++){
            citationsOnFile.get(i).setRef(references[i]);
        }

        if (toDelete.length != 0){
            performDeleteReferences(references, toDelete, citationsOnFile);
        }

        ExpositionPiece toFile = expositionPieceService.save(pieceOnFile);
        log.debug("Exposition piece citations updated");

        return "redirect:/expositions/" + toFile.getId();
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
