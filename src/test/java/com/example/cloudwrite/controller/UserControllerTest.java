package com.example.cloudwrite.controller;

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
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
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
class UserControllerTest extends SecurityCredentialsSetup {

    @Test
    void welcomePage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("welcome"));
    }

    @WithAnonymousUser
    @MethodSource("streamAllUsers")
    @ParameterizedTest
    void redirectToLoginWhenRequestingAuthenticatedPage(String username, String password) throws Exception {
        MockHttpServletRequestBuilder securedResourceAccess = get("/authenticated");

        //gather what happens when accessing /authenticated as an anonymous user
        MvcResult unauthenticatedResult = mockMvc
                .perform(securedResourceAccess)
                .andExpect(status().is4xxClientError())
                .andReturn();

        //retrieve any session data
        MockHttpSession session = (MockHttpSession) unauthenticatedResult
                .getRequest()
                .getSession();

        //post login data under same session
        mockMvc
                .perform(post("/login")
                        .param("username", username)
                        .param("password", password)
                        .session(session)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/authenticated"))
                .andReturn();

        //verify that the user session enables future access without re-logging in
        mockMvc
                .perform(securedResourceAccess.session(session))
                .andExpect(status().isOk());
    }

    @MethodSource("streamAllUsers")
    @ParameterizedTest
    void getAuthenticated(String username, String password) throws Exception {
        mockMvc.perform(get("/authenticated").with(httpBasic(username, password)))
                .andExpect(status().isOk())
                .andExpect(view().name("authenticated"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("expositions"))
                .andExpect(model().attributeExists("fundamentals"));
    }

    @MethodSource("streamAllUsers")
    @ParameterizedTest
    void getAuthenticated_searchByKeyword(String username, String password) throws Exception {
        mockMvc.perform(get("/authenticated/search").with(httpBasic(username, password))
                .param("keyWord", "sdlfjsdjfkds"))
                .andExpect(status().isOk())
                .andExpect(view().name("authenticated"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("expositions"))
                .andExpect(model().attributeExists("fundamentals"));
    }

    @MethodSource("streamAllUsers")
    @ParameterizedTest
    void getAuthenticated_searchByKeyword_blank(String username, String password) throws Exception {
        mockMvc.perform(get("/authenticated/search").with(httpBasic(username, password))
                .param("keyWord", ""))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/authenticated"));
    }

    @Test
    void getRedirectedToAuthenticated_Denied() throws Exception {
        mockMvc.perform(get("/login").with(httpBasic("admin", "sdflsjfkldsj")))
                .andExpect(status().is4xxClientError());
    }

    @MethodSource("streamAllUsers")
    @ParameterizedTest
    void userPage_withAllUsers(String username, String pwd) throws Exception {
        mockMvc.perform(get("/userPage").with(httpBasic(username, pwd)))
                .andExpect(status().isOk())
                .andExpect(view().name("userPage"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("currentUser"));
    }

    @MethodSource("streamAllUsers")
    @ParameterizedTest
    void postUpdatePassword(String username, String pwd) throws Exception {
        mockMvc.perform(post("/userPage/1/reset").with(httpBasic(username, pwd)).with(csrf())
                .param("newPassword", "sfjksdkfjsfdlj"))
                .andExpect(status().isOk())
                .andExpect(view().name("userPage"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("currentUser"));
    }

    @Test
    void logoutPage() throws Exception {
        mockMvc.perform(post("/logout").with(csrf()))
                .andExpect(status().is2xxSuccessful());
    }
}