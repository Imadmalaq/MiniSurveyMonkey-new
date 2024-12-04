package com.group23.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Import SecurityAutoConfiguration to exclude it
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@WebMvcTest(value = LoginController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Test that a GET request to "/login" returns the "auth/login" view.
     */
    @Test
    @DisplayName("GET /login should return auth/login view")
    public void testLogin() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/login"));
    }

    /**
     * Test that a GET request to "/logout" returns the "auth/login" view.
     */
    @Test
    @DisplayName("GET /logout should return auth/login view")
    public void testLogout() throws Exception {
        mockMvc.perform(get("/logout"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/login"));
    }
}
