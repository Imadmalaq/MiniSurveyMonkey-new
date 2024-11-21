package com.group23.service;

import com.group23.model.Question;
import com.group23.model.Survey;
import com.group23.repository.QuestionRepository;
import com.group23.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

/**
 * Service class for managing questions.
 */
@Service
public class QuestionService {

    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;

    /**
     * Constructor for QuestionService class.
     *
     * @param surveyRepository Repository for accessing and persisting Survey objects.
     * @param questionRepository Repository for accessing and persisting Question objects.
     */
    @Autowired
    public QuestionService(SurveyRepository surveyRepository, QuestionRepository questionRepository) {
        this.surveyRepository = surveyRepository;
        this.questionRepository = questionRepository;
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
        Survey survey = surveyRepository.findById(surveyId).orElseThrow(() ->
                new IllegalArgumentException("Survey not found with ID: " + surveyId));

        question.setSurvey(survey);
        survey.getQuestions().add(question);
        surveyRepository.save(survey);
    }

    /**
     * Retrieves a question by its ID.
     *
     * @param questionId the ID of the question to retrieve
     * @return the Question object if found
     * @throws IllegalArgumentException if no question is found with the given ID
     */
    public Question getQuestionById(Long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("Question not found with ID: " + questionId));
    }
}
