package com.group23.controller;

import com.group23.dto.QuestionForm;
import com.group23.model.*;
import com.group23.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for managing questions within surveys.
 */
@Controller
@RequestMapping("/admin/survey/{surveyId}/questions")
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    /**
     * Shows the form to add a new question to a survey.
     *
     * @param surveyId the ID of the survey
     * @param model    the model to add attributes to
     * @return the view name to render
     */
    @GetMapping("/new")
    public String showAddQuestionForm(@PathVariable Long surveyId, Model model) {
        model.addAttribute("surveyId", surveyId);
        model.addAttribute("questionForm", new QuestionForm());
        return "question/create";
    }

    /**
     * Handles the submission of a new question.
     *
     * @param surveyId    the ID of the survey
     * @param questionForm the form object populated from the form
     * @return a redirect to the survey details page
     */
    @PostMapping
    public String addQuestion(@PathVariable Long surveyId, @ModelAttribute QuestionForm questionForm) {   // binds form data from the
        Question question;

        switch (questionForm.getType()) {
            case "OpenEndedQuestion":
                System.out.println("OpenEnd");
                question = new OpenEndedQuestion();
                break;
            case "NumericRangeQuestion":
                System.out.println("Numeric");
                NumericRangeQuestion numericQuestion = new NumericRangeQuestion();
                numericQuestion.setMinValue(questionForm.getMinValue());
                numericQuestion.setMaxValue(questionForm.getMaxValue());
                question = numericQuestion;
                break;
            case "MultipleChoiceQuestion":
                System.out.println("mcq");
                MultipleChoiceQuestion mcQuestion = new MultipleChoiceQuestion();
                List<String> optionsText = questionForm.getOptions();
                List<Option> options = new ArrayList<>();
                for (String optionText : optionsText) {                                      // Iterate through the MC optionsText and add it to options ArrayList
                    Option option = new Option();
                    option.setText(optionText);
                    options.add(option);
                }
                mcQuestion.setOptions(options);
                question = mcQuestion;
                break;
            default:
                System.out.println("default");
                return "redirect:/admin/survey/" + surveyId;
        }
        question.setText(questionForm.getText());
        questionService.addQuestionToSurvey(surveyId, question);                             // Adds the questions to the specific survey
        return "redirect:/admin/survey/" + surveyId;
    }
}