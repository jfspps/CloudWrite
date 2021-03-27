package com.example.cloudwrite.controller;

import com.example.cloudwrite.model.security.User;
import com.example.cloudwrite.service.UserService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

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
        model.addAttribute("user", getUsername());
        return "userPage";
    }

    //need to prefix with ROLE_ here
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/CRUD")
    public String listUsers(Model model){
        Set<User> userSet = new HashSet<>();

        userSet.addAll(userService.findAll());
        model.addAttribute("usersFound", userSet);
        return "/userPage";
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
