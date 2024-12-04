// ResultControllerTest.java
package com.group23.controller;

import com.group23.dto.SurveyResultDTO;
import com.group23.model.Survey;
import com.group23.service.ResultService;
import com.group23.service.SurveyService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.Model;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ResultControllerTest {

    private final ResultService resultService = mock(ResultService.class);
    private final SurveyService surveyService = mock(SurveyService.class);
    private final Model model = mock(Model.class);

    private final ResultController resultController = new ResultController(resultService, surveyService);

    @Test
    public void testViewSurveyResults() {
        Long surveyId = 1L;
        Survey survey = new Survey();
        survey.setId(surveyId);
        survey.setIsOpen(false);

        SurveyResultDTO surveyResultDTO = new SurveyResultDTO();
        when(surveyService.getSurveyById(surveyId)).thenReturn(survey);
        when(resultService.generateSurveyResult(survey)).thenReturn(surveyResultDTO);

        String viewName = resultController.viewSurveyResults(surveyId, model);

        assertEquals("results/view", viewName);
        verify(model).addAttribute("surveyResult", surveyResultDTO);
        verify(model).addAttribute(eq("numericLabels"), anyMap());
        verify(model).addAttribute(eq("numericData"), anyMap());
        verify(model).addAttribute(eq("choiceLabels"), anyMap());
        verify(model).addAttribute(eq("choiceData"), anyMap());
    }

    @Test
    public void testViewSurveyResultsSurveyNotFound() {
        Long surveyId = 1L;
        when(surveyService.getSurveyById(surveyId)).thenReturn(null);

        String viewName = resultController.viewSurveyResults(surveyId, model);

        assertEquals("redirect:/api/surveys", viewName);
    }

    @Test
    public void testViewSurveyResultsSurveyOpen() {
        Long surveyId = 1L;
        Survey survey = new Survey();
        survey.setId(surveyId);
        survey.setIsOpen(true);
        when(surveyService.getSurveyById(surveyId)).thenReturn(survey);

        String viewName = resultController.viewSurveyResults(surveyId, model);

        assertEquals("redirect:/api/surveys", viewName);
    }
}
