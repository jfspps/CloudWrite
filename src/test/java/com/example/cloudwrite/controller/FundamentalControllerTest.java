package com.example.cloudwrite.controller;

import com.example.cloudwrite.model.ExpositionPiece;
import com.example.cloudwrite.model.FundamentalPiece;
import com.example.cloudwrite.model.Standfirst;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    void getNewFundamental(String username, String password) throws Exception {
        mockMvc.perform(get("/fundamentals/new").with(httpBasic(username, password)))
                .andExpect(status().isOk())
                .andExpect(view().name("/fundamentals/newFundamental"));
    }

    @MethodSource("com.example.cloudwrite.controller.SecurityCredentialsSetup#streamAllUsers")
    @ParameterizedTest
    void postNewFundamental(String username, String password) throws Exception {
        FundamentalPiece piece = FundamentalPiece.builder().build();
        FundamentalPiece savedPiece = fundamentalPieceService.save(piece);

        mockMvc.perform(post("/fundamentals/new").with(httpBasic(username, password)).with(csrf())
                .flashAttr("fundamental", piece))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/fundamentals/" + savedPiece.getId()));
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

    @MethodSource("com.example.cloudwrite.controller.SecurityCredentialsSetup#streamAllUsers")
    @ParameterizedTest
    void postUpdateFundamental(String username, String password) throws Exception {
        FundamentalPiece piece = fundamentalPieceService.findById(1L);

        mockMvc.perform(post("/fundamentals/1/update").with(httpBasic(username, password)).with(csrf())
                .flashAttr("fundamental", piece))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/fundamentals/1"));
    }

    @MethodSource("com.example.cloudwrite.controller.SecurityCredentialsSetup#streamAllUsers")
    @ParameterizedTest
    void postNewConcept(String username, String password) throws Exception {
        mockMvc.perform(post("/fundamentals/1/newConcept").with(httpBasic(username, password)).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/fundamentals/1"));
    }

    @MethodSource("com.example.cloudwrite.controller.SecurityCredentialsSetup#streamAllUsers")
    @ParameterizedTest
    void performConceptListUpdate_noDelete(String username, String password) throws Exception {
        // arguments represent pairs; concept list for fundamental piece with ID 1 has one concept*

        // to delete the first concept from a list of one expects {""}, and from two expects {"on", "", ""}
        // to delete the second concept from a list of two expects {"", "on", ""}
        // i.e. an "on" is always proceeded with an ""
        String[] deleteArray = {""};

        String[] descriptionArray = {"another new description"};
        String[] purposeArray = {"another new purpose"};
        String[] priorityArray = {"7"};

        mockMvc.perform(post("/fundamentals/1/updateConcepts").with(httpBasic(username, password)).with(csrf())
                .param("priority", priorityArray)
                .param("description", descriptionArray)
                .param("purpose", purposeArray)
                .param("deletable", deleteArray))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/fundamentals/1"));

        // confirm that that deletion of one concept was followed*
        assertEquals(1, fundamentalPieceService.findById(1L).getConceptList().size());
    }

    @MethodSource("com.example.cloudwrite.controller.SecurityCredentialsSetup#streamAllUsers")
    @ParameterizedTest
    void performConceptListUpdate_withDelete(String username, String password) throws Exception {
        // arguments represent pairs; concept list for fundamental piece with ID 1 has one concept*
        String[] deleteArray = {"on", ""};

        String[] descriptionArray = {"another new description"};
        String[] purposeArray = {"another new purpose"};
        String[] priorityArray = {"7"};

        mockMvc.perform(post("/fundamentals/1/updateConcepts").with(httpBasic(username, password)).with(csrf())
                .param("priority", priorityArray)
                .param("description", descriptionArray)
                .param("purpose", purposeArray)
                .param("deletable", deleteArray))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/fundamentals/1"));

        // confirm that that deletion of one concept was followed*
        assertEquals(0, fundamentalPieceService.findById(1L).getConceptList().size());
    }

    @MethodSource("com.example.cloudwrite.controller.SecurityCredentialsSetup#streamAllUsers")
    @ParameterizedTest
    void getDeleteFundamental(String username, String password) throws Exception {
        mockMvc.perform(get("/fundamentals/1/delete").with(httpBasic(username, password)))
                .andExpect(status().isOk())
                .andExpect(view().name("/fundamentals/confirmDelete"));
    }

    @MethodSource("com.example.cloudwrite.controller.SecurityCredentialsSetup#streamAllUsers")
    @ParameterizedTest
    void postDeleteFundamental(String username, String password) throws Exception {
        mockMvc.perform(post("/fundamentals/1/delete").with(httpBasic(username, password)).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/authenticated"));

        // removing the Chemistry article leaves one Biology article behind
        assertEquals(1, fundamentalPieceService.findAll().size());
    }
}