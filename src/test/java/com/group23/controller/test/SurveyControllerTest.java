package com.group23.controller.test;

import com.group23.controller.SurveyController;
import com.group23.model.Survey;
import com.group23.service.SurveyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


class SurveyControllerTest {

    @InjectMocks
    private SurveyController surveyController;

    @Mock
    private SurveyService surveyService;

    private Model model;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);   //Mocks SurveyController and SurveyService
        model = new BindingAwareModelMap();          // Handles model in controller classes and creates new instances of model for each method to execute

    }

    /**
     * Verifies that the correct view  name is returned.
     */
    @Test
    public void listSurveyTest(){
        List<Survey> surveys = Arrays.asList(new Survey(), new Survey()); // Creates a list of two Survey objects as mock data for the test
        when(surveyService.listAllSurveys()).thenReturn(surveys);         // Mocks the behavior of surveyService to return the list of surveys
        String viewName = surveyController.listSurveys(model);
        assertEquals("survey/list", viewName);                   // Checks if equal
    }

    /**
     * Verifies that the correct view  name is returned and that a
     * new Survey object is added to the model under the attribute
     * 'Survey'.
     */
    @Test
    void showCreateSurveyFormTest(){
        String viewName = surveyController.showCreateSurveyForm(model);     // Calls the showCreateSurveyForm method and stores it
        assertEquals("survey/create", viewName);
        assertEquals(new Survey(), model.getAttribute("survey"));
    }

    /**
     * Verifies that when a new survey is created, the view redirects
     * to the survey list page. It also checks that the newly created
     * survey is open.
     */
    @Test
    void createSurveyTest(){
        Survey survey = new Survey();
        String viewName = surveyController.createSurvey(survey);
        assertEquals("redirect:/surveys", viewName);        // Checks if method redirects to /surveys after creating a survey
        assertEquals(true, survey.getIsOpen());             // Checks if the current survey is open
    }


}