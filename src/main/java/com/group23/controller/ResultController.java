package com.group23.controller;

import com.group23.dto.SurveyResultDTO;
import com.group23.model.*;
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
        SurveyResultDTO surveyResult = resultService.generateSurveyResult(survey);

        // Prepare data for numeric range questions
        Map<Long, List<Integer>> numericLabels = new HashMap<>();
        Map<Long, List<Integer>> numericData = new HashMap<>();

        for (Question question : survey.getQuestions()) {
            if (question instanceof NumericRangeQuestion) {
                Map<Integer, Integer> counts = surveyResult.getNumericResults().get(question.getId());
                if (counts != null) {
                    // Sort the counts by key (numeric value)
                    List<Integer> labels = new ArrayList<>(counts.keySet());
                    Collections.sort(labels);
                    List<Integer> data = new ArrayList<>();
                    for (Integer label : labels) {
                        data.add(counts.get(label));
                    }
                    numericLabels.put(question.getId(), labels);
                    numericData.put(question.getId(), data);
                }
            }
        }

        // Prepare data for multiple-choice questions
        Map<Long, List<String>> choiceLabels = new HashMap<>();
        Map<Long, List<Integer>> choiceData = new HashMap<>();

        for (Question question : survey.getQuestions()) {
            if (question instanceof MultipleChoiceQuestion) {
                Map<Long, Integer> idCounts = surveyResult.getChoiceResults().get(question.getId());
                if (idCounts != null) {
                    List<String> labels = new ArrayList<>();
                    List<Integer> data = new ArrayList<>();
                    MultipleChoiceQuestion mcQuestion = (MultipleChoiceQuestion) question;

                    // Map IDs back to Option objects and sort
                    List<Option> options = new ArrayList<>();
                    for (Long optionId : idCounts.keySet()) {
                        mcQuestion.getOptions().stream()
                                .filter(option -> option.getId().equals(optionId))
                                .findFirst()
                                .ifPresent(options::add);
                    }

                    options.sort(Comparator.comparing(Option::getId));

                    // Build labels and data
                    for (Option option : options) {
                        labels.add(option.getText());
                        data.add(idCounts.get(option.getId()));
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
