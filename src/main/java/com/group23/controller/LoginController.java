package com.group23.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "auth/login"; // This corresponds to templates/login.html
    }

    @GetMapping("/logout")
    public String logout() {
        return "auth/login";
    }
}