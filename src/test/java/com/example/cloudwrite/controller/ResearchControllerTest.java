package com.example.cloudwrite.controller;

import com.example.cloudwrite.model.ResearchPiece;
import com.example.cloudwrite.model.Standfirst;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Transactional
@SpringBootTest
class ResearchControllerTest extends SecurityCredentialsSetup {

    @Test
    void getResearch_Denied() throws Exception {
        mockMvc.perform(get("/research/1"))
                .andExpect(status().is4xxClientError());
    }

    @MethodSource("com.example.cloudwrite.controller.SecurityCredentialsSetup#streamAllUsers")
    @ParameterizedTest
    void getResearch(String username, String password) throws Exception {
        mockMvc.perform(get("/research/1").with(httpBasic(username, password)))
                .andExpect(status().isOk())
                .andExpect(view().name("/research/researchDetail"))
                .andExpect(model().attributeExists("research"))
                .andExpect(model().attributeExists("results"))
                .andExpect(model().attributeExists("references"));
    }

    @MethodSource("com.example.cloudwrite.controller.SecurityCredentialsSetup#streamAllUsers")
    @ParameterizedTest
    void getNewResearch(String username, String password) throws Exception {
        mockMvc.perform(get("/research/new").with(httpBasic(username, password)))
                .andExpect(status().isOk())
                .andExpect(view().name("/research/newResearch"));
    }

    @MethodSource("com.example.cloudwrite.controller.SecurityCredentialsSetup#streamAllUsers")
    @ParameterizedTest
    void postNewResearch(String username, String password) throws Exception {
        ResearchPiece piece = ResearchPiece.builder().build();
        Standfirst standfirst = Standfirst.builder().build();

        Standfirst savedStandfirst = standfirstService.save(standfirst);
        piece.setStandfirst(savedStandfirst);
        ResearchPiece savedPiece = researchPieceService.save(piece);
        savedStandfirst.setResearchPiece(savedPiece);

        mockMvc.perform(post("/research/new").with(httpBasic(username, password)).with(csrf())
                .flashAttr("research", piece))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/research/" + savedPiece.getId()));
    }

    @MethodSource("com.example.cloudwrite.controller.SecurityCredentialsSetup#streamAllUsers")
    @ParameterizedTest
    void postNewKeyResult(String username, String password) throws Exception {
        mockMvc.perform(post("/research/1/newResult").with(httpBasic(username, password)).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/research/1"));
    }

    @MethodSource("com.example.cloudwrite.controller.SecurityCredentialsSetup#streamAllUsers")
    @ParameterizedTest
    void postNewReference(String username, String password) throws Exception {
        mockMvc.perform(post("/research/1/newReference").with(httpBasic(username, password)).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/research/1"));
    }

    @MethodSource("com.example.cloudwrite.controller.SecurityCredentialsSetup#streamAllUsers")
    @ParameterizedTest
    void postUpdateResearch(String username, String password) throws Exception {
        ResearchPiece piece = researchPieceService.findById(1L);

        mockMvc.perform(post("/research/1/update").with(httpBasic(username, password)).with(csrf())
                .flashAttr("research", piece))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/research/1"));
    }

    @MethodSource("com.example.cloudwrite.controller.SecurityCredentialsSetup#streamAllUsers")
    @ParameterizedTest
    void updateReferences(String username, String password) throws Exception {
        String[] refArray = {"update reference"};
        String[] deleteArray = {"on", "", ""};

        mockMvc.perform(post("/research/1/updateReferences").with(httpBasic(username, password)).with(csrf())
                .param("ref", refArray)
                .param("deletable", deleteArray))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/research/1"));
    }

    @MethodSource("com.example.cloudwrite.controller.SecurityCredentialsSetup#streamAllUsers")
    @ParameterizedTest
    void deleteReferences(String username, String password) throws Exception {
        String[] refArray = {"update reference"};
        String[] deleteArray = {"on", ""};

        mockMvc.perform(post("/research/1/updateReferences").with(httpBasic(username, password)).with(csrf())
                .param("ref", refArray)
                .param("deletable", deleteArray))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/research/1"));

        assertEquals(0, researchPieceService.findById(1L).getCitations().size());
    }

    // the following tests are best explained by examining the debug output from ResearchController ===============

    @MethodSource("com.example.cloudwrite.controller.SecurityCredentialsSetup#streamAllUsers")
    @ParameterizedTest
    void performDeletion_FirstOnly(String username, String password) throws Exception {
        // arguments represent pairs (see expoDetail template), the following means delete the first only
        String[] deleteArray = {"on", "", ""};
        String[] descriptionArray = {"result1", "result2"};
        String[] priorityArray = {"4", "3"};

        mockMvc.perform(post("/research/1/updateResults").with(httpBasic(username, password)).with(csrf())
                .param("priority", priorityArray)
                .param("description", descriptionArray)
                .param("deletable", deleteArray))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/research/1"));

        assertEquals(1, researchPieceService.findById(1L).getKeyResults().size());
    }

    @MethodSource("com.example.cloudwrite.controller.SecurityCredentialsSetup#streamAllUsers")
    @ParameterizedTest
    void performDeletion_SecondOnly(String username, String password) throws Exception {
        // arguments represent pairs (see expoDetail template), the following means delete the second only
        String[] deleteArray = {"", "on", ""};
        String[] descriptionArray = {"result1", "result2"};
        String[] priorityArray = {"4", "3"};

        mockMvc.perform(post("/research/1/updateResults").with(httpBasic(username, password)).with(csrf())
                .param("priority", priorityArray)
                .param("description", descriptionArray)
                .param("deletable", deleteArray))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/research/1"));

        assertEquals(1, researchPieceService.findById(1L).getKeyResults().size());
    }


    @MethodSource("com.example.cloudwrite.controller.SecurityCredentialsSetup#streamAllUsers")
    @ParameterizedTest
    void performDeletion_Both(String username, String password) throws Exception {
        // arguments represent pairs (see expoDetail template), the following means delete both results
        String[] deleteArray = {"on", "", "on", ""};
        String[] descriptionArray = {"result1", "result2"};
        String[] priorityArray = {"4", "3"};

        mockMvc.perform(post("/research/1/updateResults").with(httpBasic(username, password)).with(csrf())
                .param("priority", priorityArray)
                .param("description", descriptionArray)
                .param("deletable", deleteArray))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/research/1"));

        assertEquals(0, researchPieceService.findById(1L).getKeyResults().size());
    }

    @MethodSource("com.example.cloudwrite.controller.SecurityCredentialsSetup#streamAllUsers")
    @ParameterizedTest
    void updatePriorities(String username, String password) throws Exception {
        // change priorities from {1, 2} to {4, 3} (see Bootloader)
        String[] priorityArray = {"4", "3"};
        String[] descriptionArray = {"result1", "result2"};
        String[] deleteArray = {"on", "", "on", ""};

        mockMvc.perform(post("/research/1/updateResults").with(httpBasic(username, password)).with(csrf())
                .param("deletable", deleteArray)
                .param("description", descriptionArray)
                .param("priority", priorityArray))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/research/1"));
    }

    @MethodSource("com.example.cloudwrite.controller.SecurityCredentialsSetup#streamAllUsers")
    @ParameterizedTest
    void updateDescriptions(String username, String password) throws Exception {
        // change descriptions to {"result1", "result2"} (see Bootloader)
        String[] descriptionArray = {"result1", "result2"};
        String[] priorityArray = {"4", "3"};
        String[] deleteArray = {"on", "", "on", ""};

        mockMvc.perform(post("/research/1/updateResults").with(httpBasic(username, password)).with(csrf())
                .param("deletable", deleteArray)
                .param("priority", priorityArray)
                .param("description", descriptionArray))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/research/1"));
    }

    @MethodSource("com.example.cloudwrite.controller.SecurityCredentialsSetup#streamAllUsers")
    @ParameterizedTest
    void getDeleteResearch(String username, String password) throws Exception {
        mockMvc.perform(get("/research/1/delete").with(httpBasic(username, password)))
                .andExpect(status().isOk())
                .andExpect(view().name("/research/confirmDelete"));
    }

    @MethodSource("com.example.cloudwrite.controller.SecurityCredentialsSetup#streamAllUsers")
    @ParameterizedTest
    void postDeleteResearch(String username, String password) throws Exception {
        mockMvc.perform(post("/research/1/delete").with(httpBasic(username, password)).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/authenticated"));

        assertEquals(0, researchPieceService.findAll().size());
    }
}