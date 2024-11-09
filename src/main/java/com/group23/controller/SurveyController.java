package com.group23.controller;

import com.group23.model.Survey;
import com.group23.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing surveys.
 */
@Controller
@RequestMapping("/surveys")
public class SurveyController {

    private final SurveyService surveyService;

    @Autowired
    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
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
        model.addAttribute("surveys", surveys);
        return "survey/list";
    }

    /**
     * Shows the form to create a new survey.
     *
     * @param model the model to add attributes to
     * @return the view name to render
     */
    @GetMapping("/new")
    public String showCreateSurveyForm(Model model) {
        model.addAttribute("survey", new Survey());
        return "survey/create";
    }

    /**
     * Handles the submission of a new survey.
     *
     * @param survey the survey object populated from the form
     * @return a redirect to the survey list page
     */
    @PostMapping
    public String createSurvey(@ModelAttribute Survey survey) {
        survey.setIsOpen(true); // Set survey to open by default
        surveyService.createSurvey(survey);
        return "redirect:/surveys";
    }

    /**
     * Displays the details of a specific survey.
     *
     * @param id    the ID of the survey
     * @param model the model to add attributes to
     * @return the view name to render
     */
    @GetMapping("/{id}")
    public String viewSurvey(@PathVariable Long id, Model model) {
        Survey survey = surveyService.getSurveyById(id);
        if (survey == null) {
            return "redirect:/surveys"; // Redirect if survey not found
        }
        model.addAttribute("survey", survey);
        return "survey/view";
    }

    /**
     * Closes a survey, preventing further responses.
     *
     * @param id the ID of the survey to close
     * @return a redirect to the survey details page
     */
    @PostMapping("/{id}/close")
    public String closeSurvey(@PathVariable Long id) {
        surveyService.closeSurvey(id);
        return "redirect:/surveys/{id}";
    }
}
