package com.group23.service;

import com.group23.model.Question;
import com.group23.model.Survey;
import com.group23.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

/**
 * Service class for managing questions.
 */
@Service
public class QuestionService {

    // Repository for accessing and modifying Survey data in the database
    private final SurveyRepository surveyRepository;

    /**
     * Constructor for QuestionService class.
     *
     * @param surveyRepository Repository used to access and persist Survey objects.
     */
    @Autowired
    public QuestionService(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    /**
     * Adds a question to a specific survey by ID.
     *
     * - Retrieves the survey by ID. If the survey does not exist, throws an exception.
     * - Associates the new question with the retrieved survey.
     * - Adds the question to the survey's list of questions.
     * - Saves the survey with the updated question list in the database.
     *
     * @param surveyId ID of the survey to which the question will be added.
     * @param question Question to be added to the survey.
     * @throws IllegalArgumentException if no survey is found with the given ID.
     */
    @Transactional
    public void addQuestionToSurvey(Long surveyId, Question question) {
        // Fetch the survey by ID, or throw an exception if it doesn't exist
        Survey survey = surveyRepository.findById(surveyId).orElseThrow(() ->
                new IllegalArgumentException("Survey not found with ID: " + surveyId));

        // Set the survey in the question for bidirectional association
        question.setSurvey(survey);

        // Add the question to the survey's list of questions
        survey.getQuestions().add(question);

        // Save the updated survey to the database
        surveyRepository.save(survey);
    }
}
