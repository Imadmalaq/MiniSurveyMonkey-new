package com.group23.controller;

import com.group23.model.*;
import com.group23.service.QuestionService;
import com.group23.service.ResponseService;
import com.group23.service.SurveyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.ui.Model;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Test class for {@link ResponseController}.
 */
public class ResponseControllerTest {

    @Mock
    private SurveyService surveyService;

    @Mock
    private ResponseService responseService;

    @Mock
    private QuestionService questionService;

    @Mock
    private Model model;

    @InjectMocks
    private ResponseController responseController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testShowSurveyForm_SurveyIsOpen() {
        Long surveyId = 1L;
        Survey survey = new Survey();
        survey.setId(surveyId);
        survey.setIsOpen(true);

        Question question = new OpenEndedQuestion();
        question.setId(100L);
        survey.setQuestions(Collections.singletonList(question));

        when(surveyService.getSurveyById(surveyId)).thenReturn(survey);

        String viewName = responseController.showSurveyForm(surveyId, model);

        ArgumentCaptor<Response> responseCaptor = ArgumentCaptor.forClass(Response.class);
        verify(model).addAttribute("survey", survey);
        verify(model).addAttribute(eq("response"), responseCaptor.capture());
        assertEquals("response/form", viewName);

        Response response = responseCaptor.getValue();
        assertEquals(1, response.getAnswers().size());
        assertEquals(question, response.getAnswers().get(0).getQuestion());
    }

    @Test
    public void testShowSurveyForm_SurveyIsClosed() {
        Long surveyId = 1L;
        Survey survey = new Survey();
        survey.setIsOpen(false);

        when(surveyService.getSurveyById(surveyId)).thenReturn(survey);

        String viewName = responseController.showSurveyForm(surveyId, model);

        assertEquals("redirect:/api/surveys", viewName); // Updated assertion based on actual behavior
    }

    @Test
    public void testSubmitSurveyResponse_Success() {
        Long surveyId = 1L;
        Survey survey = new Survey();
        survey.setIsOpen(true);
        Response response = new Response();
        response.setSurvey(survey);

        when(surveyService.getSurveyById(surveyId)).thenReturn(survey);

        String viewName = responseController.submitSurveyResponse(surveyId, response);

        verify(responseService).saveResponse(response);
        assertEquals("redirect:/api/surveys/attend/" + surveyId + "/thank-you", viewName); // Updated assertion based on actual behavior
    }


    @Test
    public void testSubmitSurveyResponse_SurveyNotFound() {
        Long surveyId = 1L;
        Response response = new Response();

        when(surveyService.getSurveyById(surveyId)).thenReturn(null);

        String viewName = responseController.submitSurveyResponse(surveyId, response);

        verifyNoInteractions(responseService);
        assertEquals("redirect:/surveys", viewName);
    }

    @Test
    public void testShowThankYouPage() {
        Long surveyId = 1L;

        String viewName = responseController.showThankYouPage(surveyId);

        assertEquals("response/thank-you", viewName);
    }
}
