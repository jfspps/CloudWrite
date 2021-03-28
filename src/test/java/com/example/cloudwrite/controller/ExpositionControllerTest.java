package com.example.cloudwrite.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@Transactional
@SpringBootTest
class ExpositionControllerTest extends SecurityCredentialsSetup {

    @Test
    void getExposition_Denied() throws Exception {
        mockMvc.perform(get("/expositions/1"))
                .andExpect(status().is4xxClientError());
    }

    @MethodSource("com.example.cloudwrite.controller.SecurityCredentialsSetup#streamAllUsers")
    @ParameterizedTest
    void getRedirectedToLogin_adminPage(String username, String password) throws Exception {
        mockMvc.perform(get("/expositions/1").with(httpBasic(username, password)))
                .andExpect(status().isOk())
                .andExpect(view().name("/expositions/expoDetail"))
                .andExpect(model().attributeExists("exposition"))
                .andExpect(model().attributeExists("results"));
    }
}