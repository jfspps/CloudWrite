package com.example.cloudwrite.controller;

import com.example.cloudwrite.model.security.User;
import com.example.cloudwrite.service.UserService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;

import java.util.HashSet;
import java.util.Set;

@Controller
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    //prevent the HTTP form POST from editing listed properties
    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder){
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/adminPage")
    public String getAdminPage(Model model){
        model.addAttribute("user", getUsername());
        return "/admin/adminPage";
    }

    //need to prefix with ROLE_ here
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/CRUD")
    public String listUsers(Model model){
        Set<User> userSet = new HashSet<>();

        userSet.addAll(userService.findAll());
        model.addAttribute("usersFound", userSet);
        return "/admin/adminPage";
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
