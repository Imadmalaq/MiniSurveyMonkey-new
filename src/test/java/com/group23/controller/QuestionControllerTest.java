package com.group23.controller;

import com.group23.model.*;
import com.group23.repository.SurveyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.security.test.context.support.WithMockUser;
/**
 * Test class for {@link QuestionController}.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SurveyRepository surveyRepository;

    private Long surveyId;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    public void setup() {
        // Clear existing surveys to ensure a clean state
        surveyRepository.deleteAll();

        // Create a sample survey for the test
        Survey survey = new Survey();
        survey.setTitle("Sample Survey");
        Survey savedSurvey = surveyRepository.save(survey);
        this.surveyId = savedSurvey.getId();
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void showAddQuestionForm_shouldReturnCreateView() throws Exception {
        this.mockMvc.perform(get("/admin/survey/" + surveyId + "/questions/new"))
                .andExpect(status().isOk()) // Expect HTTP 200
                .andExpect(view().name("question/create")) // Expect the view name to match
                .andExpect(model().attributeExists("surveyId", "questionForm")); // Check model attributes
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void addNumericRangeQuestion_shouldRedirectToSurveyPage() throws Exception {
        String questionText = "What is your age?";

        this.mockMvc.perform(post("/admin/survey/" + surveyId + "/questions")
                        .param("text", questionText)
                        .param("type", "NumericRangeQuestion")
                        .param("minValue", "18")
                        .param("maxValue", "100"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/survey/" + surveyId));

        Survey survey = surveyRepository.findById(surveyId).orElse(null);
        assertThat(survey).isNotNull();
        List<Question> questions = survey.getQuestions();
        assertThat(questions).hasSize(1);
        assertThat(questions.get(0)).isInstanceOf(NumericRangeQuestion.class);
        assertThat(questions.get(0).getText()).isEqualTo(questionText);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void addOpenEndedQuestion_shouldRedirectToSurveyPage() throws Exception {
        String questionText = "What is your favorite color?";

        this.mockMvc.perform(post("/admin/survey/" + surveyId + "/questions")
                        .param("text", questionText)
                        .param("type", "OpenEndedQuestion"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/survey/" + surveyId));

        Survey survey = surveyRepository.findById(surveyId).orElse(null);
        assertThat(survey).isNotNull();
        List<Question> questions = survey.getQuestions();
        assertThat(questions).hasSize(1);
        assertThat(questions.get(0)).isInstanceOf(OpenEndedQuestion.class);
        assertThat(questions.get(0).getText()).isEqualTo(questionText);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void addMultipleChoiceQuestion_shouldRedirectToSurveyPage() throws Exception {
        // Arrange
        String questionText = "Choose your preferred fruits";
        String optionsAsString = "Apple\nBanana\nCherry"; // Options separated by newlines

        // Act
        mockMvc.perform(post("/admin/survey/" + surveyId + "/questions")
                        .param("text", questionText)
                        .param("type", "MultipleChoiceQuestion")
                        .param("optionsAsString", optionsAsString))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/survey/" + surveyId));

        // Assert (Optional)
        Survey survey = surveyRepository.findById(surveyId).orElse(null);
        assertThat(survey).isNotNull();
        List<Question> questions = survey.getQuestions();
        assertThat(questions).hasSize(1);
        assertThat(questions.get(0)).isInstanceOf(MultipleChoiceQuestion.class);
        assertThat(questions.get(0).getText()).isEqualTo(questionText);
        MultipleChoiceQuestion mcQuestion = (MultipleChoiceQuestion) questions.get(0);
        assertThat(mcQuestion.getOptions()).hasSize(3);
        assertThat(mcQuestion.getOptions())
                .extracting(Option::getText)
                .containsExactlyInAnyOrder("Apple", "Banana", "Cherry");
    }


    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void addQuestion_withInvalidType_shouldRedirectToSurveyPage() throws Exception {
        String questionText = "This should not be added";

        this.mockMvc.perform(post("/admin/survey/" + surveyId + "/questions")
                        .param("text", questionText)
                        .param("type", "InvalidQuestionType"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/survey/" + surveyId));

        Survey survey = surveyRepository.findById(surveyId).orElse(null);
        assertThat(survey).isNotNull();
        List<Question> questions = survey.getQuestions();
        assertThat(questions).isEmpty();
    }
}
