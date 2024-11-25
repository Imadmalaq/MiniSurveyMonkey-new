package com.group23.controller;

import com.group23.model.*;
import com.group23.service.QuestionService;
import com.group23.service.ResponseService;
import com.group23.service.SurveyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Test class for {@link ResponseController}.
 * <p>
 * This class contains unit tests for the ResponseController, verifying
 * the correct handling of displaying survey forms and submitting responses.
 * It uses Mockito to mock dependencies and assert the expected outcomes.
 * </p>
 */
public class ResponseControllerTest {

    // Mock dependencies
    @Mock
    private SurveyService surveyService;

    @Mock
    private ResponseService responseService;

    @Mock
    private QuestionService questionService;

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

    /**
     * Test case: Show survey form when the survey is open.
     * <p>
     * Verifies that when an open survey is requested, the controller
     * returns the correct view and adds the necessary model attributes.
     * </p>
     */
    @Test
    public void testShowSurveyForm_SurveyIsOpen() {
        Long surveyId = 1L;
        Survey survey = new Survey();
        survey.setId(surveyId);
        survey.setIsOpen(true);  // Set survey as open

        // Create a mock question
        Question question = mock(Question.class);
        when(question.getId()).thenReturn(100L);

        // Add the question to the survey
        survey.setQuestions(Collections.singletonList(question));

        // Mock the behavior of surveyService to return an open survey
        when(surveyService.getSurveyById(surveyId)).thenReturn(survey);

        // Call the method to be tested
        String viewName = responseController.showSurveyForm(surveyId, model);

        // Verify model attributes and check the expected view
        ArgumentCaptor<Response> responseCaptor = ArgumentCaptor.forClass(Response.class);
        verify(model).addAttribute(eq("survey"), eq(survey));
        verify(model).addAttribute(eq("response"), responseCaptor.capture());
        assertEquals("response/form", viewName);

        // Verify that the response has answers initialized
        Response response = responseCaptor.getValue();
        assertEquals(1, response.getAnswers().size());
        assertEquals(question, response.getAnswers().get(0).getQuestion());
    }

    /**
     * Test case: Redirect to main surveys page when survey is closed.
     * <p>
     * Verifies that when a closed survey is requested, the controller
     * redirects to the surveys listing page.
     * </p>
     */
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


    /**
     * Test case: Redirect without saving when survey is closed.
     * <p>
     * Verifies that submitting a response to a closed or non-existent survey
     * does not save the response and redirects to the surveys listing page.
     * </p>
     */
    @Test
    public void testSubmitSurveyResponse_SurveyIsClosed() {
        Long surveyId = 1L;
        Survey survey = new Survey();
        survey.setIsOpen(false);  // Set survey as closed
        Response response = new Response();

        // Mock the behavior of surveyService to return a closed survey
        when(surveyService.getSurveyById(surveyId)).thenReturn(null); // Simulate survey not found

        // Call the method to be tested
        String viewName = responseController.submitSurveyResponse(surveyId, response);

        // Check that the redirection view is returned and response is not saved
        verifyNoInteractions(responseService);
        assertEquals("redirect:/surveys", viewName);
    }

    /**
     * Test case: Show thank-you page.
     * <p>
     * Verifies that the controller returns the correct view
     * when showing the thank-you page after survey submission.
     * </p>
     */
    @Test
    public void testShowThankYouPage() {
        Long surveyId = 1L;

        // Call the method to be tested
        String viewName = responseController.showThankYouPage(surveyId);

        // Check for the expected thank-you view
        assertEquals("response/thank-you", viewName);
    }
}
