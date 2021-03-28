package com.example.cloudwrite.controller;

import com.example.cloudwrite.model.ExpositionPiece;
import com.example.cloudwrite.model.FundamentalPiece;
import com.example.cloudwrite.model.security.User;
import com.example.cloudwrite.service.ExpositionPieceService;
import com.example.cloudwrite.service.FundamentalPieceService;
import com.example.cloudwrite.service.UserService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ExpositionPieceService expositionPieceService;
    private final FundamentalPieceService fundamentalPieceService;
    private final PasswordEncoder DBpasswordEncoder = new BCryptPasswordEncoder();

    //prevent the HTTP form POST from editing listed properties
    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder){
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping({"/","/welcome"})
    public String welcomePage(){
        return "welcome";
    }

    @GetMapping("/authenticated")
    public String getAuthenticatedPage(Model model){
        List<ExpositionPiece> expositionPieces = new ArrayList<>(expositionPieceService.findAll());
        Collections.sort(expositionPieces);
        model.addAttribute("expositions", expositionPieces);

        List<FundamentalPiece> fundamentalPieces = new ArrayList<>(fundamentalPieceService.findAll());
        Collections.sort(fundamentalPieces);
        model.addAttribute("fundamentals", fundamentalPieces);

        model.addAttribute("user", getUsername());
        return "authenticated";
    }

    //this overrides the default Spring Security login page
    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    // see SecurityConfiguration for redirection details
    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    //this overrides the default GET logout page
    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "welcome";
    }

    @GetMapping("/userPage")
    public String userPage(Model model) {
        // reserve 'user' here (do not use 'user' with other references)
        model.addAttribute("user", getUsername());
        model.addAttribute("currentUser", userService.findByUserName(getUsername()));
        return "userPage";
    }

    @PostMapping("/userPage/{id}/reset")
    public String postResetPassword(@PathVariable("id") String ID, String newPassword, Model model) throws NotFoundException {
        if (userService.findById(Long.valueOf(ID)) == null){
            throw new NotFoundException("User not found");
        }

        User found = userService.findById(Long.valueOf(ID));
        String reply = "";
        User saved;

        if (newPassword.length() > 7){
            found.setPassword(DBpasswordEncoder.encode(newPassword));
            saved = userService.save(found);
            reply = "Password updated";
        } else {
            saved = found;
            reply = "Password must be at least eight characters in length";
        }

        model.addAttribute("user", saved.getUsername());
        model.addAttribute("currentUser", saved);
        model.addAttribute("reply", reply);
        return "userPage";
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
