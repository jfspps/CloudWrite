package com.example.cloudwrite.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
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

@Slf4j
@SpringBootTest
// note that these are mock tests and pass when using in-memory H2 database only
class UserControllerTest {

    @Autowired
    WebApplicationContext context;

    protected MockMvc mockMvc;

    private final static String ADMINPWD = "admin123";
    private final static String USERPWD = "user123";

    public static Stream<Arguments> streamAllUsers() {
        return Stream.of(Arguments.of("admin", ADMINPWD),
                Arguments.of("user", USERPWD));
    }

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
    void getRedirectedToLogin_authenticated(String username, String password) throws Exception {
        mockMvc.perform(get("/authenticated").with(httpBasic(username, password)).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("authenticated"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    void getRedirectedToAuthenticated_Denied() throws Exception {
        mockMvc.perform(get("/login").with(httpBasic("admin", USERPWD)))
                .andExpect(status().is4xxClientError());
    }

    @MethodSource("streamAllUsers")
    @ParameterizedTest
    void userPage_withAllUsers(String username, String pwd) throws Exception {
        mockMvc.perform(get("/userPage").with(httpBasic(username, pwd)))
                .andExpect(status().isOk())
                .andExpect(view().name("userPage"));
    }

    @Test
    void logoutPage() throws Exception {
        mockMvc.perform(post("/logout").with(csrf()))
                .andExpect(status().is2xxSuccessful());
    }
}