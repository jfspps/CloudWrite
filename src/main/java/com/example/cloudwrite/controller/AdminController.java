package com.example.cloudwrite.controller;

import com.example.cloudwrite.model.security.User;
import com.example.cloudwrite.service.UserService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

//need to prefix with ROLE_ here
@Secured("ROLE_ADMIN")
@RequiredArgsConstructor
@Controller
public class AdminController {

    private final UserService userService;

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

    @GetMapping("/listUsers")
    public String listUsers(Model model){
        List<User> userSet = new ArrayList<>(userService.findAll());

        Collections.sort(userSet);

        model.addAttribute("usersFound", userSet);
        return "/admin/adminPage";
    }

    @GetMapping("/getUserDetails/{id}")
    public String listUsers(@PathVariable("id") String ID, Model model) throws NotFoundException {
        if (userService.findById(Long.valueOf(ID)) == null){
            throw new NotFoundException("User not found");
        }

        User found = userService.findById(Long.valueOf(ID));

        List<User> userSet = new ArrayList<>(userService.findAll());
        Collections.sort(userSet);

        model.addAttribute("usersFound", userSet);
        model.addAttribute("chosenUser", found);
        return "/admin/adminPage";
    }

    @PostMapping("/adminPage/{id}/update")
    public String postUpdateUser(@ModelAttribute("chosenUser") User chosenUser, @PathVariable("id") String ID, Model model) throws NotFoundException {
        if (userService.findById(Long.valueOf(ID)) == null){
            throw new NotFoundException("User not found");
        }

        User found = userService.findById(Long.valueOf(ID));
        if (!chosenUser.getUsername().isBlank() && userService.findByUserName(chosenUser.getUsername()) == null) {
            found.setUsername(chosenUser.getUsername());
        }

        found.setEnabled(chosenUser.getEnabled());
        userService.save(found);

        List<User> userSet = new ArrayList<>(userService.findAll());
        Collections.sort(userSet);

        model.addAttribute("usersFound", userSet);
        model.addAttribute("chosenUser", found);
        model.addAttribute("reply", "Confirmed");
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
