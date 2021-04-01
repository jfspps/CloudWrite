package com.example.cloudwrite.controller;

import com.example.cloudwrite.model.ExpositionPiece;
import com.example.cloudwrite.model.KeyResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
                .andExpect(model().attributeExists("results"))
                .andExpect(model().attributeExists("references"));
    }

    @MethodSource("com.example.cloudwrite.controller.SecurityCredentialsSetup#streamAllUsers")
    @ParameterizedTest
    void postUpdateExposition(String username, String password) throws Exception {
        ExpositionPiece piece = expositionPieceService.findById(1L);

        mockMvc.perform(post("/expositions/1/update").with(httpBasic(username, password)).with(csrf())
                .flashAttr("exposition", piece))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/expositions/1"));
    }

    // the following tests are best explained by examining the debug output from ExpositionController ===============

    @MethodSource("com.example.cloudwrite.controller.SecurityCredentialsSetup#streamAllUsers")
    @ParameterizedTest
    void performDeletion_FirstOnly(String username, String password) throws Exception {
        // arguments represent pairs (see expoDetail template), the following means delete the first only
        String[] deleteArray = {"on", "", ""};
        String[] descriptionArray = {"result1", "result2"};
        String[] priorityArray = {"4", "3"};

        mockMvc.perform(post("/expositions/1/updateResults").with(httpBasic(username, password)).with(csrf())
                .param("priority", priorityArray)
                .param("description", descriptionArray)
                .param("deletable", deleteArray))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/expositions/1"));

        assertEquals(1, expositionPieceService.findById(1L).getKeyResults().size());
    }

    @MethodSource("com.example.cloudwrite.controller.SecurityCredentialsSetup#streamAllUsers")
    @ParameterizedTest
    void performDeletion_SecondOnly(String username, String password) throws Exception {
        // arguments represent pairs (see expoDetail template), the following means delete the second only
        String[] deleteArray = {"", "on", ""};
        String[] descriptionArray = {"result1", "result2"};
        String[] priorityArray = {"4", "3"};

        mockMvc.perform(post("/expositions/1/updateResults").with(httpBasic(username, password)).with(csrf())
                .param("priority", priorityArray)
                .param("description", descriptionArray)
                .param("deletable", deleteArray))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/expositions/1"));

        assertEquals(1, expositionPieceService.findById(1L).getKeyResults().size());
    }


    @MethodSource("com.example.cloudwrite.controller.SecurityCredentialsSetup#streamAllUsers")
    @ParameterizedTest
    void performDeletion_Both(String username, String password) throws Exception {
        // arguments represent pairs (see expoDetail template), the following means delete both results
        String[] deleteArray = {"on", "", "on", ""};
        String[] descriptionArray = {"result1", "result2"};
        String[] priorityArray = {"4", "3"};

        mockMvc.perform(post("/expositions/1/updateResults").with(httpBasic(username, password)).with(csrf())
                .param("priority", priorityArray)
                .param("description", descriptionArray)
                .param("deletable", deleteArray))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/expositions/1"));

        assertEquals(0, expositionPieceService.findById(1L).getKeyResults().size());
    }

    @MethodSource("com.example.cloudwrite.controller.SecurityCredentialsSetup#streamAllUsers")
    @ParameterizedTest
    void updatePriorities(String username, String password) throws Exception {
        // change priorities from {1, 2} to {4, 3} (see Bootloader)
        String[] priorityArray = {"4", "3"};
        String[] descriptionArray = {"result1", "result2"};
        String[] deleteArray = {"on", "", "on", ""};

        mockMvc.perform(post("/expositions/1/updateResults").with(httpBasic(username, password)).with(csrf())
                .param("deletable", deleteArray)
                .param("description", descriptionArray)
                .param("priority", priorityArray))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/expositions/1"));
    }

    @MethodSource("com.example.cloudwrite.controller.SecurityCredentialsSetup#streamAllUsers")
    @ParameterizedTest
    void updateDescriptions(String username, String password) throws Exception {
        // change descriptions to {"result1", "result2"} (see Bootloader)
        String[] descriptionArray = {"result1", "result2"};
        String[] priorityArray = {"4", "3"};
        String[] deleteArray = {"on", "", "on", ""};

        mockMvc.perform(post("/expositions/1/updateResults").with(httpBasic(username, password)).with(csrf())
                .param("deletable", deleteArray)
                .param("priority", priorityArray)
                .param("description", descriptionArray))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/expositions/1"));
    }
}