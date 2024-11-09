package com.group23.service;

import com.group23.model.Question;
import com.group23.model.Survey;
import com.group23.repository.SurveyRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for managing questions.
 */
@Service
public class QuestionService {

    private final SurveyRepository surveyRepository;

    @Autowired
    public QuestionService(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    @Transactional
    public void addQuestionToSurvey(Long surveyId, Question question) {
        Survey survey = surveyRepository.findById(surveyId).orElseThrow(() ->
                new IllegalArgumentException("Survey not found with ID: " + surveyId));
        question.setSurvey(survey);
        survey.getQuestions().add(question);
        surveyRepository.save(survey);
    }
}
