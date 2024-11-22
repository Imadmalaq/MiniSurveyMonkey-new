package com.group23.controller;

import com.group23.model.Answer;
import com.group23.model.Question;
import com.group23.model.Response;
import com.group23.model.Survey;
import com.group23.service.QuestionService;
import com.group23.service.ResponseService;
import com.group23.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // Correct import
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyEditorSupport;

/**
 * Controller for handling user responses to surveys.
 */
@Controller
@RequestMapping("/surveys/{surveyId}/respond")
public class ResponseController {

    private final SurveyService surveyService;
    private final ResponseService responseService;
    @Autowired
    private QuestionService questionService;


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
        response.setSurvey(survey);

        // Initialize answers for each question
        for (Question question : survey.getQuestions()) {
            Answer answer = new Answer();
            // Use the specific question type directly
            answer.setQuestion(question); // Do not create a new Question object
            response.addAnswer(answer);
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
    ////NEWW CODEEEEEE

    @PostMapping
    public String submitSurveyResponse(@PathVariable Long surveyId, @ModelAttribute("response") Response response) {
        Survey survey = surveyService.getSurveyById(surveyId);
        if (survey == null) {
            System.out.println("Survey not found or is closed.");
            return "redirect:/surveys";
        }
        System.out.println("Survey Retrieved: " + survey);

        response.setSurvey(survey);

        for (Answer answer : response.getAnswers()) {
            System.out.println("DEBUG: Received Answer: " + answer);
            if (answer.getQuestion() != null) {
                System.out.println("DEBUG: Question ID: " + answer.getQuestion().getId());
            } else {
                System.out.println("DEBUG: Question is null.");
            }
            //throw new IllegalArgumentException("Question ID is missing for an answer.");




           Long questionId = answer.getQuestion().getId();
            Question question = questionService.getQuestionById(questionId);
            if (question == null) {
                System.out.println("Question not found with ID: " + questionId);
                throw new IllegalArgumentException("Question not found with ID: " + questionId);
            }
            answer.setQuestion(question);
            answer.setResponse(response);
            System.out.println("Answer Updated: " + answer);
        }

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

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Question.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                if (text == null || text.isEmpty()) {
                    setValue(null);
                } else {
                    Long id = Long.valueOf(text);
                    setValue(questionService.getQuestionById(id)); // Fetch Question from DB
                }
            }
        });
    }

}
