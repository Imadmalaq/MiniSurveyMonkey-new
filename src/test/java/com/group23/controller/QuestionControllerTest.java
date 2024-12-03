package com.group23.controller;

import com.group23.model.*;
import com.group23.repository.SurveyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// Uncomment the next line if you decide to switch to WebMvcTest and mock dependencies
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

// For verifying data persistence (Optional)
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// For MockMvcResultMatchers
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for {@link QuestionController}.
 * <p>
 * This class contains unit tests for the QuestionController, verifying
 * the correct handling of adding different types of questions to a survey.
 * It uses MockMvc to simulate HTTP requests and asserts the expected outcomes.
 * </p>
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional // <-- Add this annotation
public class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SurveyRepository surveyRepository;

    private Long surveyId;

    /**
     * Sets up the test environment before each test.
     * <p>
     * This method cleans the survey repository to ensure a fresh state and
     * creates a new survey, storing its ID for use in the tests.
     * </p>
     */
//    @BeforeEach
//    public void setup() {
//        // Clean the repository to avoid interference from other tests
//        surveyRepository.deleteAll();
//
//        // Set up initial data for the test
//        Survey survey = new Survey();
//        survey.setTitle("Sample Survey");
//        Survey savedSurvey = surveyRepository.save(survey);
//        this.surveyId = savedSurvey.getId();
//    }
//
//    /**
//     * Tests that the GET request to show the add question form returns the correct view and model attributes.
//     *
//     * @throws Exception if an error occurs during the request
//     */
//    @Test
//    public void showAddQuestionForm_shouldReturnCreateView() throws Exception {
//        // Act & Assert
//        this.mockMvc.perform(get("/surveys/" + surveyId + "/questions/new"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("question/create"))
//                .andExpect(model().attributeExists("surveyId", "questionForm"));
//    }
//
//    /**
//     * Tests adding a {@link NumericRangeQuestion} to the survey.
//     * <p>
//     * Verifies that a POST request with valid numeric range question data
//     * redirects to the survey page and that the question is added to the survey.
//     * </p>
//     *
//     * @throws Exception if an error occurs during the request
//     */
//    @Test
//    public void addNumericRangeQuestion_shouldRedirectToSurveyPage() throws Exception {
//        // Arrange
//        String questionText = "What is your age?";
//
//        // Act
//        mockMvc.perform(post("/surveys/" + surveyId + "/questions")
//                        .param("text", questionText)
//                        .param("type", "NumericRangeQuestion")
//                        .param("minValue", "18")
//                        .param("maxValue", "100"))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/surveys/" + surveyId));
//
//        // Assert (Optional: Verify that the question was added)
//        Survey survey = surveyRepository.findById(surveyId).orElse(null);
//        assertThat(survey).isNotNull();
//        List<Question> questions = survey.getQuestions();
//        assertThat(questions).hasSize(1);
//        assertThat(questions.get(0)).isInstanceOf(NumericRangeQuestion.class);
//        assertThat(questions.get(0).getText()).isEqualTo(questionText);
//    }
//
//    /**
//     * Tests adding an {@link OpenEndedQuestion} to the survey.
//     * <p>
//     * Ensures that a POST request with valid open-ended question data
//     * redirects to the survey page and that the question is added correctly.
//     * </p>
//     *
//     * @throws Exception if an error occurs during the request
//     */
//    @Test
//    public void addOpenEndedQuestion_shouldRedirectToSurveyPage() throws Exception {
//        // Arrange
//        String questionText = "What is your favorite color?";
//
//        // Act
//        mockMvc.perform(post("/surveys/" + surveyId + "/questions")
//                        .param("text", questionText)
//                        .param("type", "OpenEndedQuestion"))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/surveys/" + surveyId));
//
//        // Assert (Optional)
//        Survey survey = surveyRepository.findById(surveyId).orElse(null);
//        assertThat(survey).isNotNull();
//        List<Question> questions = survey.getQuestions();
//        assertThat(questions).hasSize(1);
//        assertThat(questions.get(0)).isInstanceOf(OpenEndedQuestion.class);
//        assertThat(questions.get(0).getText()).isEqualTo(questionText);
//    }
//
//    /**
//     * Tests adding a {@link MultipleChoiceQuestion} to the survey.
//     * <p>
//     * Verifies that a POST request with multiple choice question data,
//     * including options, redirects to the survey page and that the question
//     * and options are added correctly.
//     * </p>
//     *
//     * @throws Exception if an error occurs during the request
//     */
//    @Test
//    public void addMultipleChoiceQuestion_shouldRedirectToSurveyPage() throws Exception {
//        // Arrange
//        String questionText = "Choose your preferred fruits";
//        String optionsAsString = "Apple\nBanana\nCherry"; // Options separated by newlines
//
//        // Act
//        mockMvc.perform(post("/surveys/" + surveyId + "/questions")
//                        .param("text", questionText)
//                        .param("type", "MultipleChoiceQuestion")
//                        .param("optionsAsString", optionsAsString))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/surveys/" + surveyId));
//
//        // Assert (Optional)
//        Survey survey = surveyRepository.findById(surveyId).orElse(null);
//        assertThat(survey).isNotNull();
//        List<Question> questions = survey.getQuestions();
//        assertThat(questions).hasSize(1);
//        assertThat(questions.get(0)).isInstanceOf(MultipleChoiceQuestion.class);
//        assertThat(questions.get(0).getText()).isEqualTo(questionText);
//        MultipleChoiceQuestion mcQuestion = (MultipleChoiceQuestion) questions.get(0);
//        assertThat(mcQuestion.getOptions()).hasSize(3);
//        assertThat(mcQuestion.getOptions())
//                .extracting(Option::getText)
//                .containsExactlyInAnyOrder("Apple", "Banana", "Cherry");
//    }
//
//    /**
//     * Tests adding a question with an invalid type.
//     * <p>
//     * Ensures that the controller handles invalid question types gracefully
//     * by redirecting to the survey page without adding the question.
//     * </p>
//     *
//     * @throws Exception if an error occurs during the request
//     */
//    @Test
//    public void addQuestion_withInvalidType_shouldRedirectToSurveyPage() throws Exception {
//        // Arrange
//        String questionText = "This should not be added";
//
//        // Act
//        mockMvc.perform(post("/surveys/" + surveyId + "/questions")
//                        .param("text", questionText)
//                        .param("type", "InvalidQuestionType"))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/surveys/" + surveyId));
//
//        // Assert (Optional)
//        Survey survey = surveyRepository.findById(surveyId).orElse(null);
//        assertThat(survey).isNotNull();
//        List<Question> questions = survey.getQuestions();
//        assertThat(questions).isEmpty();
//    }
//
}
