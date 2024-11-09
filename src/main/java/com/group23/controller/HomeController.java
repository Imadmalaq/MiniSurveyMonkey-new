package com.group23.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for handling home page requests.
 */
@Controller
public class HomeController {

    /**
     * Displays the home page of the application.
     *
     * @return the view name to render
     */
    @GetMapping("/")
    public String home() {
        return "home/index"; // This corresponds to templates/home/index.html
    }
}
