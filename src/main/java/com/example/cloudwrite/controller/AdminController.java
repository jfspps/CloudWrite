package com.example.cloudwrite.controller;

import com.example.cloudwrite.model.security.User;
import com.example.cloudwrite.service.UserService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder DBpasswordEncoder = new BCryptPasswordEncoder();

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
        String reply = "";

        if (!chosenUser.getUsername().isBlank() && userService.findByUserName(chosenUser.getUsername()) == null) {
            found.setUsername(chosenUser.getUsername());
            reply += "Username updated";
        } else {
            reply += "Username cannot be blank or is already in use";
        }

        found.setEnabled(chosenUser.getEnabled());
        userService.save(found);

        List<User> userSet = new ArrayList<>(userService.findAll());
        Collections.sort(userSet);

        model.addAttribute("usersFound", userSet);
        model.addAttribute("chosenUser", found);
        model.addAttribute("reply", reply);
        return "/admin/adminPage";
    }

    @PostMapping("/adminPage/{id}/reset")
    public String postResetUserPassword(@PathVariable("id") String ID, String suffix, Model model) throws NotFoundException {
        if (userService.findById(Long.valueOf(ID)) == null){
            throw new NotFoundException("User not found");
        }

        User found = userService.findById(Long.valueOf(ID));
        String newPassword = found.getUsername() + suffix;
        String reply;
        User saved;

        if (newPassword.length() > 7){
            found.setPassword(DBpasswordEncoder.encode(newPassword));
            saved = userService.save(found);
            reply = "Password was reset";
        } else {
            reply = "Username + suffix must be at least 8 characters in length";
            saved = found;
        }

        List<User> userSet = new ArrayList<>(userService.findAll());
        Collections.sort(userSet);

        model.addAttribute("usersFound", userSet);
        model.addAttribute("chosenUser", saved);
        model.addAttribute("reply", reply);
        return "/admin/adminPage";
    }

    @PostMapping("/adminPage/newUser")
    public String postNewUser(String userName, String suffix, Model model){
        String reply;
        User saved;

        if (userName.isBlank() || userService.findByUserName(userName) != null){
            reply = "Username and password suffix does not adhere to requirements";
            saved = null;
        } else {
            String newPassword = DBpasswordEncoder.encode(userName + suffix);
            User newUser = User.builder().username(userName).password(newPassword).build();
            saved = userService.save(newUser);
            reply = "New user, " + saved.getUsername() + ", saved.";
        }

        List<User> userSet = new ArrayList<>(userService.findAll());
        model.addAttribute("usersFound", userSet);
        model.addAttribute("chosenUser", saved);
        model.addAttribute("reply", reply);
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
