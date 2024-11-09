package com.group23.controller;

import com.group23.model.Answer;
import com.group23.model.Question;
import com.group23.model.Response;
import com.group23.model.Survey;
import com.group23.service.ResponseService;
import com.group23.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling user responses to surveys.
 */
@Controller
@RequestMapping("/surveys/{surveyId}/respond")
public class ResponseController {

    private final SurveyService surveyService;
    private final ResponseService responseService;

    @Autowired
    public ResponseController(SurveyService surveyService, ResponseService responseService) {
        this.surveyService = surveyService;
        this.responseService = responseService;
    }

    /**
     * Displays the survey form for users to fill out.
     *
     * @param surveyId the ID of the survey
     * @param model    the model to add attributes to
     * @return the view name to render
     */
    @GetMapping
    public String showSurveyForm(@PathVariable Long surveyId, Model model) {
        Survey survey = surveyService.getSurveyById(surveyId);
        if (survey == null || !survey.getIsOpen()) {
            return "redirect:/surveys";
        }
        Response response = new Response();
        // Initialize answers for each question
        for (Question question : survey.getQuestions()) {
            Answer answer = new Answer();
            answer.setQuestionId(question.getId());
            response.getAnswers().put(question.getId(), answer);
        }
        model.addAttribute("survey", survey);
        model.addAttribute("response", response);
        return "response/form";
    }

    /**
     * Handles the submission of a user's survey responses.
     *
     * @param surveyId the ID of the survey
     * @param response the response object populated from the form
     * @return a redirect to a thank-you page
     */
    @PostMapping
    public String submitSurveyResponse(@PathVariable Long surveyId, @ModelAttribute("response") Response response) {
        Survey survey = surveyService.getSurveyById(surveyId);
        if (survey == null || !survey.getIsOpen()) {
            return "redirect:/surveys";
        }
        response.setSurvey(survey);
        responseService.saveResponse(response);
        return "redirect:/surveys/" + surveyId + "/respond/thank-you";
    }

    /**
     * Displays a thank-you page after the survey is submitted.
     *
     * @param surveyId the ID of the survey
     * @return the view name to render
     */
    @GetMapping("/thank-you")
    public String showThankYouPage(@PathVariable Long surveyId) {
        return "response/thank-you";
    }
}
