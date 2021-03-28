package com.example.cloudwrite.controller;

import com.example.cloudwrite.model.security.User;
import com.example.cloudwrite.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
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
@SpringBootTest
@Transactional
class AdminControllerTest {

    @Autowired
    WebApplicationContext context;

    protected MockMvc mockMvc;

    private final static String ADMINPWD = "admin123";

    @Autowired
    private UserService userService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    //this fails with Spring Security with any username ('random' is effectively replaced with anyString())
    @WithMockUser("random")
    @Test
    void loginPage_random() throws Exception {
        mockMvc.perform(get("/adminPage"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void getRedirectedToLogin_adminPage() throws Exception {
        mockMvc.perform(get("/adminPage").with(httpBasic("admin", ADMINPWD)))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/adminPage"));
    }

    @Test
    void getListUsers() throws Exception {
        mockMvc.perform(get("/listUsers").with(httpBasic("admin", ADMINPWD)))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/adminPage"))
                .andExpect(model().attributeExists("usersFound"));
    }

    // note that this fails when connected to a persistent MySQL db
    @Test
    void getUserDetails() throws Exception {
        mockMvc.perform(get("/getUserDetails/1").with(httpBasic("admin", ADMINPWD)))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/adminPage"))
                .andExpect(model().attributeExists("usersFound"))
                .andExpect(model().attributeExists("chosenUser"));
    }

    @Test
    void postUpdateUser() throws Exception {
        User user = userService.findById(1L);

        mockMvc.perform(post("/adminPage/1/update").with(httpBasic("admin", ADMINPWD)).with(csrf())
                .param("userName", "fskfksdjklsdfkl")
                .flashAttr("chosenUser", user))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/adminPage"))
                .andExpect(model().attributeExists("usersFound"))
                .andExpect(model().attributeExists("chosenUser"))
                .andExpect(model().attributeExists("reply"));
    }
}