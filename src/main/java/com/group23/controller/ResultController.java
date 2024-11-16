package com.group23.controller;

import com.group23.dto.SurveyResult;
import com.group23.model.Option;
import com.group23.model.Question;
import com.group23.model.Survey;
import com.group23.service.ResultService;
import com.group23.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.*;

/**
 * Controller for displaying survey results.
 */
@Controller
@RequestMapping("/surveys/{surveyId}/results")
public class ResultController {

    private final ResultService resultService;
    private final SurveyService surveyService;

    @Autowired
    public ResultController(ResultService resultService, SurveyService surveyService) {
        this.resultService = resultService;
        this.surveyService = surveyService;
    }

    /**
     * Displays the compiled results of a survey.
     *
     * @param surveyId the ID of the survey
     * @param model    the model to add attributes to
     * @return the view name to render
     */
    @GetMapping
    public String viewSurveyResults(@PathVariable Long surveyId, Model model) {
        // Retrieve the survey
        Survey survey = surveyService.getSurveyById(surveyId);

        // Check if survey exists and is closed
        if (survey == null || survey.getIsOpen()) {
            // If the survey does not exist or is still open, redirect or show an error
            return "redirect:/surveys";
        }

        // Generate the survey results
        SurveyResult surveyResult = resultService.generateSurveyResult(survey);

        // Prepare data for numeric range questions
        Map<Long, List<Integer>> numericLabels = new HashMap<>();
        Map<Long, List<Integer>> numericData = new HashMap<>();

        for (Question question : survey.getQuestions()) {
            if (question instanceof com.group23.model.NumericRangeQuestion) {
                Map<Integer, Integer> counts = surveyResult.getNumericResults().get(question.getId());
                if (counts != null) {
                    List<Integer> labels = new ArrayList<>(counts.keySet());
                    List<Integer> data = new ArrayList<>(counts.values());
                    numericLabels.put(question.getId(), labels);
                    numericData.put(question.getId(), data);
                }
            }
        }

        // Prepare data for multiple-choice questions
        Map<Long, List<String>> choiceLabels = new HashMap<>();
        Map<Long, List<Integer>> choiceData = new HashMap<>();

        for (Question question : survey.getQuestions()) {
            if (question instanceof com.group23.model.MultipleChoiceQuestion) {
                Map<Option, Integer> counts = surveyResult.getChoiceResults().get(question.getId());
                if (counts != null) {
                    List<String> labels = new ArrayList<>();
                    List<Integer> data = new ArrayList<>();
                    for (Option option : counts.keySet()) {
                        labels.add(option.getText());
                        data.add(counts.get(option));
                    }
                    choiceLabels.put(question.getId(), labels);
                    choiceData.put(question.getId(), data);
                }
            }
        }

        // Add the survey results and prepared data to the model
        model.addAttribute("surveyResult", surveyResult);
        model.addAttribute("numericLabels", numericLabels);
        model.addAttribute("numericData", numericData);
        model.addAttribute("choiceLabels", choiceLabels);
        model.addAttribute("choiceData", choiceData);

        // Return the view to display the results
        return "results/view";
    }
}
