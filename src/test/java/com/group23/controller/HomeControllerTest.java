package com.group23.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get; // Import for GET requests
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status; // Import for status assertions
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view; // Import for view assertions

/**
 * Test class for HomeController.
 */
@WebMvcTest(HomeController.class) // Specifies that only HomeController should be loaded
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc; // Injects the MockMvc instance

    /**
     * Test that a GET request to "/" returns the "home/index" view.
     *
     * @throws Exception if the request fails
     */
//    @Test
//    @DisplayName("GET / should return home/index view")
//    public void testHomeController() throws Exception {
//        mockMvc.perform(get("/")) // Perform GET request to "/"
//                .andExpect(status().isOk()) // Expect HTTP 200 OK status
//                .andExpect(view().name("home/index")); // Expect the view name to be "home/index"
//    }
}
