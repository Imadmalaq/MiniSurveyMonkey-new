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
        Map<Long, List<String>> openEndedResults = new HashMap<>();      // Stores responses to open-ended questions
        Map<Long, Map<Integer, Integer>> numericResults = new HashMap<>(); // Stores counts of numeric responses
        Map<Long, Map<Long, Integer>> choiceResults = new HashMap<>();     // Stores counts of choices selected in multiple-choice questions

        // Process each response in the survey
        for (Response response : responses) {
            for (Answer answer : response.getAnswers().values()) {
                Long questionId = answer.getQuestionId();

                // Retrieve the question associated with the answer
                Question question = getQuestionById(survey, questionId);

                // Handle responses based on the question type
                if (question instanceof OpenEndedQuestion) {
                    // Collect text answers for open-ended questions
                    openEndedResults.computeIfAbsent(questionId, k -> new ArrayList<>())
                            .add(answer.getText());
                } else if (question instanceof NumericRangeQuestion) {
                    // Count occurrences of each numeric response
                    Integer value = answer.getNumber();
                    if (value != null) {
                        numericResults.computeIfAbsent(questionId, k -> new HashMap<>())
                                .merge(value, 1, Integer::sum); // Increment count for the numeric answer
                    }
                } else if (question instanceof MultipleChoiceQuestion) {
                    // Count selections for each option in multiple-choice questions
                    Long optionId = answer.getSelectedOptionId();
                    if (optionId != null) {
                        choiceResults.computeIfAbsent(questionId, k -> new HashMap<>())
                                .merge(optionId, 1, Integer::sum); // Increment count for the selected option
                    }
                }
            }
        }

        // Set results into the SurveyResult object
        surveyResult.setOpenEndedResults(openEndedResults);
        surveyResult.setNumericResults(numericResults);
        surveyResult.setChoiceResults(choiceResults);

        // Return the completed survey results
        return surveyResult;
    }

    /**
     * Helper method to retrieve a question from the survey by ID.
     *
     * @param survey     the survey containing the questions
     * @param questionId the ID of the question to retrieve
     * @return the Question object
     */
    private Question getQuestionById(Survey survey, Long questionId) {
        for (Question question : survey.getQuestions()) {
            if (question.getId().equals(questionId)) {
                return question;
            }
        }
        throw new NoSuchElementException("Question not found with ID: " + questionId);
    }
}
