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
    public void showCreateSurveyFormTest(){
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
    public void createSurveyTest(){
        Survey survey = new Survey();
        String viewName = surveyController.createSurvey(survey);
        assertEquals("redirect:/surveys", viewName);        // Checks if method redirects to /surveys after creating a survey
        assertEquals(true, survey.getIsOpen());             // Checks if the current survey is open
    }

    /**
     * Checks that when a survey is found, the correct view is returned.
     * Also checks that the survey is added to the model with the correct
     * attribute name.
     */
    @Test
    public void viewSurveyTest(){
        Survey survey = new Survey();
        when(surveyService.getSurveyById(1L)).thenReturn(survey);       // Mocks service to return a Survey object when the survey with ID 1L is requested
        String viewName = surveyController.viewSurvey(1L, model);    // Returns the view to variable viewName
        assertEquals("survey/view", viewName);
        assertEquals(survey, model.getAttribute("survey"));
    }

    /**
     * Checks the case for when a survey is not found.
     * Ensures that when a survey is not found, user is redirected
     * to the survey list page.
     */
    @Test
    public void viewSurveyTestNotFound(){
        when(surveyService.getSurveyById(1L)).thenReturn(null);
        String viewName = surveyController.viewSurvey(1L, model);
        assertEquals("redirect:/surveys", viewName);
    }

    /**
     * Checks that when a survey is closed, it automatically redirects
     * to the Survey page.
     */
    @Test
    void closeSurveyTest(){
        String viewName = surveyController.closeSurvey(1L);
        assertEquals("redirect:/surveys/{id}", viewName);       // Checks that it redirects to the Survey page, with the specific ID
    }
}