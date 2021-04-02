package com.example.cloudwrite.controller;

import com.example.cloudwrite.service.ExpositionPieceService;
import com.example.cloudwrite.service.StandfirstService;
import com.example.cloudwrite.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.stream.Stream;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

public abstract class SecurityCredentialsSetup {

    @Autowired
    WebApplicationContext context;

    @Autowired
    UserService userService;

    @Autowired
    ExpositionPieceService expositionPieceService;

    @Autowired
    StandfirstService standfirstService;

    protected MockMvc mockMvc;

    private final static String ADMINPWD = "admin123";
    private final static String USERPWD = "user123";

    public static Stream<Arguments> streamAllUsers() {
        return Stream.of(Arguments.of("admin", ADMINPWD),
                Arguments.of("user", USERPWD));
    }

    public static Stream<Arguments> streamAdminUsers() {
        return Stream.of(Arguments.of("admin", ADMINPWD));
    }

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }
}
