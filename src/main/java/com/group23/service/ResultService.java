package com.group23.service;

import com.group23.dto.SurveyResult;
import com.group23.model.*;
import com.group23.repository.ResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service class for generating and retrieving survey results.
 */
@Service
public class ResultService {

    private final ResponseRepository responseRepository;

    /**
     * Constructs a new ResultService with the given repository.
     *
     * @param responseRepository the repository for responses
     */
    @Autowired
    public ResultService(ResponseRepository responseRepository) {
        this.responseRepository = responseRepository;
    }

    /**
     * Generates survey results for a closed survey.
     *
     * This method calculates and organizes the results from responses to a survey.
     * It processes different types of questions: open-ended, numeric range, and multiple-choice.
     * The results are structured in maps according to the question type and returned
     * as a SurveyResult object.
     *
     * @param survey The survey for which results are generated.
     * @return The generated SurveyResult containing the processed survey results.
     */
    public SurveyResult generateSurveyResult(Survey survey) {
        // Retrieve all responses associated with the given survey
        List<Response> responses = responseRepository.findBySurvey(survey);

        // Initialize the SurveyResult object to hold survey details and results
        SurveyResult surveyResult = new SurveyResult();
        surveyResult.setSurvey(survey);

        // Initialize result maps for different question types
        Map<Long, List<String>> openEndedResults = new HashMap<>();
        Map<Long, Map<Integer, Integer>> numericResults = new HashMap<>();
        Map<Long, Map<Option, Integer>> choiceResults = new HashMap<>();

        // Process each response in the survey
        for (Response response : responses) {
            for (Answer answer : response.getAnswers()) {
                Long questionId = answer.getQuestion().getId();
                // Retrieve the question associated with the answer
                Question question = answer.getQuestion();

                if (question instanceof OpenEndedQuestion) {
                    openEndedResults.computeIfAbsent(questionId, k -> new ArrayList<>())
                            .add(answer.getText());
                } else if (question instanceof NumericRangeQuestion) {
                    Integer value = answer.getNumber();
                    if (value != null) {
                        numericResults.computeIfAbsent(questionId, k -> new TreeMap<>())
                                .merge(value, 1, Integer::sum);
                    }
                } else if (question instanceof MultipleChoiceQuestion) {
                    Long optionId = answer.getSelectedOptionId();
                    if (optionId != null) {
                        MultipleChoiceQuestion mcQuestion = (MultipleChoiceQuestion) question;
                        Option selectedOption = getOptionById(mcQuestion, optionId);
                        choiceResults.computeIfAbsent(questionId, k -> new HashMap<>())
                                .merge(selectedOption, 1, Integer::sum);
                    }
                }
            }
        }

        // Set results into the SurveyResult object
        surveyResult.setOpenEndedResults(openEndedResults);
        surveyResult.setNumericResults(numericResults);
        surveyResult.setChoiceResults(choiceResults);

        return surveyResult;
    }

    /**
     * Helper method to retrieve an option from a multiple-choice question by ID.
     *
     * @param question the multiple-choice question
     * @param optionId the ID of the option to retrieve
     * @return the Option object
     */
    private Option getOptionById(MultipleChoiceQuestion question, Long optionId) {
        return question.getOptions().stream()
                .filter(option -> option.getId().equals(optionId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Option not found with ID: " + optionId));
    }
}
