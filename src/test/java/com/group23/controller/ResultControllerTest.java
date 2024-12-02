package com.group23.controller;

import com.group23.dto.SurveyResultDTO;
import com.group23.model.*;
import com.group23.service.ResultService;
import com.group23.service.SurveyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.hasKey;


import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for {@link ResultController}.
 * <p>
 * This class contains unit tests for the ResultController, verifying
 * the correct handling of displaying survey results under various scenarios.
 * It uses MockMvc to simulate HTTP requests and Mockito to mock dependencies.
 * </p>
 */
@WebMvcTest(ResultController.class)
public class ResultControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ResultService resultService;

    @MockBean
    private SurveyService surveyService;

    /**
     * Tests viewing survey results when the survey exists and is closed.
     * <p>
     * Verifies that the controller returns the correct view and model attributes
     * when a closed survey with results is requested.
     * </p>
     *
     * @throws Exception if an error occurs during the request
     */
//    @Test
//    public void viewSurveyResults_SurveyExistsAndClosed() throws Exception {
//        // Arrange
//        Long surveyId = 1L;
//        Survey survey = new Survey();
//        survey.setId(surveyId);
//        survey.setIsOpen(false); // Survey is closed
//
//        // Add questions to the survey
//        List<Question> questions = new ArrayList<>();
//
//        // Numeric Range Question
//        NumericRangeQuestion nrQuestion = new NumericRangeQuestion();
//        nrQuestion.setId(101L);
//        nrQuestion.setText("How satisfied are you?");
//        nrQuestion.setMinValue(1);
//        nrQuestion.setMaxValue(5);
//        questions.add(nrQuestion);
//
//        // Multiple Choice Question
//        MultipleChoiceQuestion mcQuestion = new MultipleChoiceQuestion();
//        mcQuestion.setId(102L);
//        mcQuestion.setText("What is your favorite color?");
//        Option option1 = new Option();
//        option1.setId(201L);
//        option1.setText("Red");
//        Option option2 = new Option();
//        option2.setId(202L);
//        option2.setText("Blue");
//        mcQuestion.setOptions(Arrays.asList(option1, option2));
//        questions.add(mcQuestion);
//
//        survey.setQuestions(questions);
//
//        // Mock surveyService to return the survey
//        when(surveyService.getSurveyById(surveyId)).thenReturn(survey);
//
//        // Mock resultService to return survey results
//        SurveyResultDTO surveyResultDTO = new SurveyResultDTO();
//        surveyResultDTO.setSurvey(survey);
//
//        // Mock numeric results
//        Map<Long, Map<Integer, Integer>> numericResults = new HashMap<>();
//        Map<Integer, Integer> nrCounts = new HashMap<>();
//        nrCounts.put(1, 5);
//        nrCounts.put(2, 10);
//        nrCounts.put(3, 15);
//        numericResults.put(nrQuestion.getId(), nrCounts);
//        surveyResultDTO.setNumericResults(numericResults);
//
//        // Mock choice results
//        Map<Long, Map<Long, Integer>> choiceResults = new HashMap<>();
//        Map<Long, Integer> mcCounts = new HashMap<>();
//        mcCounts.put(option1.getId(), 8);
//        mcCounts.put(option2.getId(), 12);
//        choiceResults.put(mcQuestion.getId(), mcCounts);
//        surveyResultDTO.setChoiceResults(choiceResults);
//
//        // Mock resultService to return the surveyResultDTO
//        when(resultService.generateSurveyResult(survey)).thenReturn(surveyResultDTO);
//
//        // Act & Assert
//        mockMvc.perform(get("/surveys/{surveyId}/results", surveyId))
//                .andExpect(status().isOk())
//                .andExpect(view().name("results/view"))
//                .andExpect(model().attributeExists("surveyResult"))
//                .andExpect(model().attributeExists("numericLabels"))
//                .andExpect(model().attributeExists("numericData"))
//                .andExpect(model().attributeExists("choiceLabels"))
//                .andExpect(model().attributeExists("choiceData"))
//                // Additional assertions to verify model attributes
//                .andExpect(model().attribute("surveyResult", surveyResultDTO))
//                .andExpect(model().attribute("numericLabels", hasKey(nrQuestion.getId())))
//                .andExpect(model().attribute("choiceLabels", hasKey(mcQuestion.getId())));
//    }
//
//    /**
//     * Tests viewing survey results when the survey does not exist.
//     * <p>
//     * Verifies that the controller redirects to the surveys page
//     * when the requested survey does not exist.
//     * </p>
//     *
//     * @throws Exception if an error occurs during the request
//     */
//    @Test
//    public void viewSurveyResults_SurveyDoesNotExist() throws Exception {
//        // Arrange
//        Long surveyId = 1L;
//
//        // Mock surveyService to return null
//        when(surveyService.getSurveyById(surveyId)).thenReturn(null);
//
//        // Act & Assert
//        mockMvc.perform(get("/surveys/{surveyId}/results", surveyId))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/surveys"));
//    }
//
//    /**
//     * Tests viewing survey results when the survey is still open.
//     * <p>
//     * Verifies that the controller redirects to the surveys page
//     * when the requested survey is still open.
//     * </p>
//     *
//     * @throws Exception if an error occurs during the request
//     */
//    @Test
//    public void viewSurveyResults_SurveyIsOpen() throws Exception {
//        // Arrange
//        Long surveyId = 1L;
//        Survey survey = new Survey();
//        survey.setId(surveyId);
//        survey.setIsOpen(true); // Survey is open
//
//        // Mock surveyService to return the survey
//        when(surveyService.getSurveyById(surveyId)).thenReturn(survey);
//
//        // Act & Assert
//        mockMvc.perform(get("/surveys/{surveyId}/results", surveyId))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/surveys"));
//    }
//
}
