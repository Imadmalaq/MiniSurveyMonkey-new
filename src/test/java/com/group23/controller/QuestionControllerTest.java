package com.group23.controller;

import com.group23.model.Survey;
import com.group23.repository.SurveyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private SurveyRepository surveyRepository;

    @BeforeEach
    public void setup() {
        // Set up initial data for the test
        Survey survey = new Survey();
        survey.setTitle("Sample Survey");
        surveyRepository.save(survey);
    }

    @Test
    public void showAddQuestionForm_shouldReturnCreateView() throws Exception {
        // Arrange
        Long surveyId = 1L; // Assuming the survey with ID 1 exists

        // Act & Assert
        this.mockMvc.perform(get("/surveys/1/questions/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("question/create"))
                .andExpect(model().attributeExists("surveyId", "questionForm"));
    }

    @Test
    public void addQuestion_shouldRedirectToSurveyPage() throws Exception {
        // Arrange
        Long surveyId = 1L; // Assuming the survey with ID 1 exists
        String questionText = "What is your age?";

        // Act & Assert
        mockMvc.perform(post("/surveys/1/questions")
                        .param("text", questionText)
                        .param("type", "NumericRangeQuestion")
                        .param("minValue", "18")
                        .param("maxValue", "100"))
                .andExpect(status().is3xxRedirection())  // Expecting a redirection status
                .andExpect(redirectedUrl("/surveys/" + surveyId));
    }



}
