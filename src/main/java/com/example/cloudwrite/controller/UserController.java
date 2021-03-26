package com.example.cloudwrite.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserController {

    @GetMapping({"/","/welcome"})
    public String welcomePage(){
        return "welcome";
    }

    @GetMapping("/authenticated")
    public String getAuthenticatedPage(Model model){
        model.addAttribute("user", getUsername());
        return "authenticated";
    }

    @GetMapping("/adminPage")
    public String getAdminPage(Model model){
        model.addAttribute("user", getUsername());
        return "/admin/adminPage";
    }

    //this overrides the default Spring Security login page
    @GetMapping("/login")
    public String loginPage(){
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

    private String getUsername(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails)principal).getUsername();
        } else {
            return principal.toString();
        }
    }
}
