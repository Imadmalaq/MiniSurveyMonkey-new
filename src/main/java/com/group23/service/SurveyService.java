package com.group23.service;

import com.group23.model.Survey;
import com.group23.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Service class for handling survey operations.
 */
@Service
public class SurveyService {

    private final SurveyRepository surveyRepository;

    /**
     * Constructs a new SurveyService with the given SurveyRepository.
     *
     * @param surveyRepository the repository for surveys
     */
    @Autowired
    public SurveyService(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    /**
     * Creates a new survey.
     *
     * @param survey the survey to create
     * @return the created survey
     */
    public Survey createSurvey(Survey survey) {
        return surveyRepository.save(survey);
    }

    /**
     * Retrieves a survey by its ID.
     *
     * @param id the ID of the survey
     * @return the survey, or null if not found
     */
    public Survey getSurveyById(Long id) {
        return surveyRepository.findById(id).orElse(null);
    }

    /**
     * Updates an existing survey.
     *
     * @param survey the survey to update
     * @return the updated survey
     */
    public Survey updateSurvey(Survey survey) {
        return surveyRepository.save(survey);
    }

    /**
     * Deletes a survey by its ID.
     *
     * @param id the ID of the survey to delete
     */
    public void deleteSurvey(Long id) {
        surveyRepository.deleteById(id);
    }

    /**
     * Lists all surveys.
     *
     * @return a list of all surveys
     */
    public List<Survey> listAllSurveys() {
        return surveyRepository.findAll();
    }

    /**
     * Closes a survey, preventing new responses.
     *
     * @param id the ID of the survey to close
     */
    public void closeSurvey(Long id) {
        Survey survey = getSurveyById(id);
        if (survey != null) {
            survey.setIsOpen(false);
            surveyRepository.save(survey);
        }
    }
}
