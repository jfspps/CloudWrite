package com.example.cloudwrite.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@Slf4j
@Transactional
@SpringBootTest
class FundamentalControllerTest extends SecurityCredentialsSetup {

    @Test
    void getFundamentalArticle_Denied() throws Exception {
        mockMvc.perform(get("/fundamentals/1"))
                .andExpect(status().is4xxClientError());
    }

    @MethodSource("com.example.cloudwrite.controller.SecurityCredentialsSetup#streamAllUsers")
    @ParameterizedTest
    void getFundamental(String username, String password) throws Exception {
        mockMvc.perform(get("/fundamentals/1").with(httpBasic(username, password)))
                .andExpect(status().isOk())
                .andExpect(view().name("/fundamentals/fundamentalDetail"))
                .andExpect(model().attributeExists("fundamental"))
                .andExpect(model().attributeExists("concepts"));
    }
}