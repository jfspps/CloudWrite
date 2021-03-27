package com.example.cloudwrite.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@SpringBootTest
// note that these are mock tests and pass when using in-memory H2 database only
class UserControllerTest {

    @Autowired
    WebApplicationContext context;

    protected MockMvc mockMvc;

    private final String ADMINPWD = "admin123";
    private final String USERPWD = "user123";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @WithAnonymousUser
    @Test
    void welcomePage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("welcome"));
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
    void getRedirectedToLogin_authenticated() throws Exception{
        mockMvc.perform(get("/authenticated").with(httpBasic("user", USERPWD)).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("authenticated"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    void getRedirectedToAuthenticated_Denied() throws Exception {
        mockMvc.perform(get("/login").with(httpBasic("admin", USERPWD)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void logoutPage() throws Exception {
        mockMvc.perform(post("/logout").with(csrf()))
                .andExpect(status().is2xxSuccessful());
    }
}