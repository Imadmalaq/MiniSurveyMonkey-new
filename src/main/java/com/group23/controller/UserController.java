package com.group23.controller;

import com.group23.model.Answer;
import com.group23.model.Response;
import com.group23.model.Survey;
import com.group23.model.User;
import com.group23.service.ResponseService;
import com.group23.service.SurveyService;
import com.group23.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/surveys")
public class UserController {

    private final UserService userService;
    private final ResponseService responseService;
    private final SurveyService surveyService;


    @Autowired
    public UserController(UserService userService, ResponseService responseService, SurveyService surveyService) {
        this.userService = userService;
        this.responseService = responseService;
        this.surveyService = surveyService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/users/login";
    }

    @GetMapping("/getActive")
    @PreAuthorize("hasRole('USER')")                    // Only person with role as USER can access this method
    public ResponseEntity<List<Survey>> getActiveSurveys() {
        List<Survey> surveys = surveyService.listAllSurveys();
        return ResponseEntity.ok(surveys);
    }

    @PostMapping("/{id}/submit")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Response> submitResponse(
            @PathVariable Long id,
            @RequestBody List<Answer> answers,
            @AuthenticationPrincipal User user,
            @ModelAttribute("response") Response response) {
        Response surveyResponse = responseService.saveResponse(response);
        return ResponseEntity.status(HttpStatus.CREATED).body(surveyResponse);
    }

    @PostMapping("/login")
    public String customLogin(
            @RequestParam String username,
            @RequestParam String password
    ) {
        // Additional custom login validation
        if (userService.validateAdminCredentials(username, password)) {
            // Perform admin-specific actions if needed
            return "redirect:/admin/survey";
        }
        return "redirect:/login?error=true";
    }


    /**
     * Displays a list of all surveys.
     *
     * @param model the model to add attributes to
     * @return the view name to render
     */
    @GetMapping
    public String listSurveys(Model model) {
        List<Survey> surveys = surveyService.listAllSurveys();
        surveys.forEach(survey->System.out.println(survey+"survey"));
        model.addAttribute("surveys", surveys);
        return "home/home";
    }
}