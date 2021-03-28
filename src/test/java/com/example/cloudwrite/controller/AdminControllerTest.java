package com.example.cloudwrite.controller;

import com.example.cloudwrite.model.security.User;
import com.example.cloudwrite.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;
import java.util.stream.Stream;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Cross-site request forgery, also known as session riding (sometimes pronounced sea-surf) or XSRF, is a type of
// malicious exploit of a website where unauthorized commands are submitted from a user that the web application trusts.
// POST requests, with csrf enabled, will be denied (HTTP 403) in the browser but likely pass in Spring MVC tests
// (tests bypass Spring security); if POST fails in the browser, add:
// <input type="hidden" th:name="${_csrf.parameterName}" th:value="${_csrf.token}" />
// immediately after any <input> tags which represent POST requests (the above fragment adds the requisite info to Model)
@Slf4j
@Transactional
@SpringBootTest
class AdminControllerTest extends SecurityCredentialsSetup {

    //this fails with Spring Security with any username ('random' is effectively replaced with anyString())
    @WithMockUser("random")
    @Test
    void loginPage_random() throws Exception {
        mockMvc.perform(get("/adminPage"))
                .andExpect(status().is4xxClientError());
    }

    @MethodSource("com.example.cloudwrite.controller.SecurityCredentialsSetup#streamAdminUsers")
    @ParameterizedTest
    void getRedirectedToLogin_adminPage(String username, String password) throws Exception {
        mockMvc.perform(get("/adminPage").with(httpBasic(username, password)))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/adminPage"));
    }

    @MethodSource("com.example.cloudwrite.controller.SecurityCredentialsSetup#streamAdminUsers")
    @ParameterizedTest
    void getListUsers(String username, String password) throws Exception {
        mockMvc.perform(get("/listUsers").with(httpBasic(username, password)))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/adminPage"))
                .andExpect(model().attributeExists("usersFound"));
    }

    // note that this fails when connected to a persistent MySQL db
    @MethodSource("com.example.cloudwrite.controller.SecurityCredentialsSetup#streamAdminUsers")
    @ParameterizedTest
    void getUserDetails(String username, String password) throws Exception {
        mockMvc.perform(get("/getUserDetails/1").with(httpBasic(username, password)))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/adminPage"))
                .andExpect(model().attributeExists("usersFound"))
                .andExpect(model().attributeExists("chosenUser"));
    }

    @MethodSource("com.example.cloudwrite.controller.SecurityCredentialsSetup#streamAdminUsers")
    @ParameterizedTest
    void postUpdateUser(String username, String password) throws Exception {
        User user = userService.findById(1L);

        mockMvc.perform(post("/adminPage/1/update").with(httpBasic(username, password)).with(csrf())
                .param("userName", "fskfksdjklsdfkl")
                .flashAttr("chosenUser", user))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/adminPage"))
                .andExpect(model().attributeExists("usersFound"))
                .andExpect(model().attributeExists("chosenUser"))
                .andExpect(model().attributeExists("reply"));
    }

    @MethodSource("com.example.cloudwrite.controller.SecurityCredentialsSetup#streamAdminUsers")
    @ParameterizedTest
    void postResetUserPassword(String username, String password) throws Exception {
        User user = userService.findById(1L);

        mockMvc.perform(post("/adminPage/1/reset").with(httpBasic(username, password)).with(csrf())
                .param("suffix", "fskfksdjklsdfkl")
                .flashAttr("chosenUser", user))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/adminPage"))
                .andExpect(model().attributeExists("usersFound"))
                .andExpect(model().attributeExists("chosenUser"))
                .andExpect(model().attributeExists("reply"));
    }

    @MethodSource("com.example.cloudwrite.controller.SecurityCredentialsSetup#streamAdminUsers")
    @ParameterizedTest
    void postNewUser(String username, String password) throws Exception {
        mockMvc.perform(post("/adminPage/newUser").with(httpBasic(username, password)).with(csrf())
                .param("suffix", "fskfksdjklsdfkl")
                .param("userName", "fsdjfjsd"))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/adminPage"))
                .andExpect(model().attributeExists("usersFound"))
                .andExpect(model().attributeExists("chosenUser"))
                .andExpect(model().attributeExists("reply"));
    }
}