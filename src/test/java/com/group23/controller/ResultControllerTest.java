package com.group23.controller;

import com.group23.dto.SurveyResult;
import com.group23.model.*;
import com.group23.service.ResultService;
import com.group23.service.SurveyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ResultControllerTest {

    @Mock
    private ResultService resultService;

    @Mock
    private SurveyService surveyService;

    @Mock
    private Model model;

    @InjectMocks
    private ResultController resultController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testViewSurveyResults_SurveyNotFound() {
        Long surveyId = 1L;
        when(surveyService.getSurveyById(surveyId)).thenReturn(null);

        String result = resultController.viewSurveyResults(surveyId, model);

        assertEquals("redirect:/surveys", result);
        verify(surveyService).getSurveyById(surveyId);
        verifyNoInteractions(resultService);
    }

    @Test
    void testViewSurveyResults_SurveyIsOpen() {
        Long surveyId = 1L;
        Survey survey = new Survey();
        survey.setIsOpen(true);
        when(surveyService.getSurveyById(surveyId)).thenReturn(survey);

        String result = resultController.viewSurveyResults(surveyId, model);

        assertEquals("redirect:/surveys", result);
        verify(surveyService).getSurveyById(surveyId);
        verifyNoInteractions(resultService);
    }

    @Test
    void testViewSurveyResults_Success() {
        Long surveyId = 1L;
        Survey survey = new Survey();
        survey.setId(surveyId);
        survey.setIsOpen(false);

        // Create numeric question
        NumericRangeQuestion numericQuestion = new NumericRangeQuestion();
        numericQuestion.setId(1L);
        numericQuestion.setText("Numeric Question");

        // Create multiple-choice question
        MultipleChoiceQuestion choiceQuestion = new MultipleChoiceQuestion();
        choiceQuestion.setId(2L);
        choiceQuestion.setText("Choice Question");

        // Create options for the multiple-choice question
        Option option1 = new Option();
        option1.setId(1L);
        option1.setText("Option 1");

        Option option2 = new Option();
        option2.setId(2L);
        option2.setText("Option 2");

        choiceQuestion.setOptions(Arrays.asList(option1, option2));

        // Add questions to survey
        survey.setQuestions(Arrays.asList(numericQuestion, choiceQuestion));

        // Mock survey result
        SurveyResult surveyResult = new SurveyResult();
        surveyResult.setNumericResults(Map.of(
                numericQuestion.getId(), Map.of(1, 5, 2, 3)
        ));
        surveyResult.setChoiceResults(Map.of(
                choiceQuestion.getId(), Map.of(option1, 7, option2, 2)
        ));

        when(surveyService.getSurveyById(surveyId)).thenReturn(survey);
        when(resultService.generateSurveyResult(survey)).thenReturn(surveyResult);

        String result = resultController.viewSurveyResults(surveyId, model);

        assertEquals("results/view", result);
        verify(surveyService).getSurveyById(surveyId);
        verify(resultService).generateSurveyResult(survey);

        // Verify numeric results
        verify(model).addAttribute(eq("numericLabels"), any());
        verify(model).addAttribute(eq("numericData"), any());

        // Verify choice results
        verify(model).addAttribute(eq("choiceLabels"), any());
        verify(model).addAttribute(eq("choiceData"), any());

        verify(model).addAttribute("surveyResult", surveyResult);
    }

}
