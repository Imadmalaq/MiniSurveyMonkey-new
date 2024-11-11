package com.group23.controller;

import com.group23.model.Question;
import com.group23.model.Response;
import com.group23.model.Survey;
import com.group23.service.ResponseService;
import com.group23.service.SurveyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ResponseControllerTest {

    // Mock dependencies
    @Mock
    private SurveyService surveyService;

    @Mock
    private ResponseService responseService;

    @Mock
    private Model model;

    // Inject mocks into the ResponseController instance
    @InjectMocks
    private ResponseController responseController;

    // Set up mocks before each test
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test case: Show survey form when the survey is open
    @Test
    public void testShowSurveyForm_SurveyIsOpen() {
        Long surveyId = 1L;
        Survey survey = new Survey();
        survey.setIsOpen(true);  // Set survey as open
        survey.setQuestions(Collections.singletonList(mock(Question.class)));  // Mock a Question instance

        // Mock the behavior of surveyService to return an open survey
        when(surveyService.getSurveyById(surveyId)).thenReturn(survey);

        // Call the method to be tested
        String viewName = responseController.showSurveyForm(surveyId, model);

        // Verify model attributes and check the expected view
        verify(model).addAttribute(eq("survey"), eq(survey));
        verify(model).addAttribute(eq("response"), any(Response.class));
        assertEquals("response/form", viewName);
    }

    // Test case: Redirect to main surveys page when survey is closed
    @Test
    public void testShowSurveyForm_SurveyIsClosed() {
        Long surveyId = 1L;
        Survey survey = new Survey();
        survey.setIsOpen(false);  // Set survey as closed

        // Mock the behavior of surveyService to return a closed survey
        when(surveyService.getSurveyById(surveyId)).thenReturn(survey);

        // Call the method to be tested
        String viewName = responseController.showSurveyForm(surveyId, model);

        // Check that the redirection view is returned
        assertEquals("redirect:/surveys", viewName);
    }

    // Test case: Submit a response when the survey is open
    @Test
    public void testSubmitSurveyResponse_SurveyIsOpen() {
        Long surveyId = 1L;
        Survey survey = new Survey();
        survey.setIsOpen(true);  // Set survey as open
        Response response = new Response();

        // Mock the behavior of surveyService to return an open survey
        when(surveyService.getSurveyById(surveyId)).thenReturn(survey);

        // Call the method to be tested
        String viewName = responseController.submitSurveyResponse(surveyId, response);

        // Verify that responseService saves the response and check for the expected redirect
        verify(responseService).saveResponse(response);
        assertEquals("redirect:/surveys/" + surveyId + "/respond/thank-you", viewName);
    }

    // Test case: Redirect without saving when survey is closed
    @Test
    public void testSubmitSurveyResponse_SurveyIsClosed() {
        Long surveyId = 1L;
        Survey survey = new Survey();
        survey.setIsOpen(false);  // Set survey as closed
        Response response = new Response();

        // Mock the behavior of surveyService to return a closed survey
        when(surveyService.getSurveyById(surveyId)).thenReturn(survey);

        // Call the method to be tested
        String viewName = responseController.submitSurveyResponse(surveyId, response);

        // Check that the redirection view is returned and response is not saved
        assertEquals("redirect:/surveys", viewName);
    }

    // Test case: Show thank-you page
    @Test
    public void testShowThankYouPage() {
        Long surveyId = 1L;

        // Call the method to be tested
        String viewName = responseController.showThankYouPage(surveyId);

        // Check for the expected thank-you view
        assertEquals("response/thank-you", viewName);
    }
}
