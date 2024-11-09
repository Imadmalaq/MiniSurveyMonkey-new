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
     * @param survey the survey to generate results for
     * @return the survey results
     */
    public SurveyResult generateSurveyResult(Survey survey) {
        List<Response> responses = responseRepository.findBySurvey(survey);

        SurveyResult surveyResult = new SurveyResult();
        surveyResult.setSurvey(survey);

        Map<Long, List<String>> openEndedResults = new HashMap<>();
        Map<Long, Map<Integer, Integer>> numericResults = new HashMap<>();
        Map<Long, Map<Long, Integer>> choiceResults = new HashMap<>();

        for (Response response : responses) {
            for (Answer answer : response.getAnswers().values()) {
                Long questionId = answer.getQuestionId();
                Question question = getQuestionById(survey, questionId);

                if (question instanceof OpenEndedQuestion) {
                    openEndedResults.computeIfAbsent(questionId, k -> new ArrayList<>())
                            .add(answer.getText());
                } else if (question instanceof NumericRangeQuestion) {
                    Integer value = answer.getNumber();
                    if (value != null) {
                        numericResults.computeIfAbsent(questionId, k -> new HashMap<>())
                                .merge(value, 1, Integer::sum);
                    }
                } else if (question instanceof MultipleChoiceQuestion) {
                    Long optionId = answer.getSelectedOptionId();
                    if (optionId != null) {
                        choiceResults.computeIfAbsent(questionId, k -> new HashMap<>())
                                .merge(optionId, 1, Integer::sum);
                    }
                }
            }
        }

        surveyResult.setOpenEndedResults(openEndedResults);
        surveyResult.setNumericResults(numericResults);
        surveyResult.setChoiceResults(choiceResults);

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
