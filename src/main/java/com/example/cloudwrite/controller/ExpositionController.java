package com.example.cloudwrite.controller;

import com.example.cloudwrite.model.*;
import com.example.cloudwrite.model.security.User;
import com.example.cloudwrite.service.*;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@Secured({"ROLE_ADMIN", "ROLE_USER"})
@RequestMapping("/expositions")
public class ExpositionController {

    private final UserService userService;
    private final ExpositionPieceService expositionPieceService;
    private final KeyResultService keyResultService;
    private final StandfirstService standfirstService;

    //prevent the HTTP form POST from editing listed properties
    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder){
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/{id}")
    public String getExposition(@PathVariable("id") String ID, Model model) throws NotFoundException {
        if (expositionPieceService.findById(Long.valueOf(ID)) == null){
            throw new NotFoundException("Resource not found");
        }

        ExpositionPiece piece = expositionPieceService.findById(Long.valueOf(ID));

        // sort by priority
        List<KeyResult> results = piece.getKeyResults();
        Collections.sort(results);

        List<Citation> citations = piece.getCitations();
        Collections.sort(citations);

        model.addAttribute("references", citations);
        model.addAttribute("exposition", piece);
        model.addAttribute("results", results);
        return "/expositions/expoDetail";
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


    private String getUsername(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails)principal).getUsername();
        } else {
            return principal.toString();
        }
    }
}
