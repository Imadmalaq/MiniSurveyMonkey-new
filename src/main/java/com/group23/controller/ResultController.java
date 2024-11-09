package com.group23.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for displaying survey results.
 */
@Controller
@RequestMapping("/surveys/{surveyId}/results")
public class ResultController {

    /**
     * Displays the compiled results of a survey.
     *
     * @param surveyId the ID of the survey
     * @param model    the model to add attributes to
     * @return the view name to render
     */
    @GetMapping
    public String viewSurveyResults(@PathVariable Long surveyId, Model model) {
        // Method implementation goes here
        return "results/view";
    }
}
