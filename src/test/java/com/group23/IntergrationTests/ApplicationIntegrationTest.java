package com.group23.IntergrationTests;

import com.group23.MiniSurveyMonkeyApplication;
import com.group23.model.*;
import com.group23.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = MiniSurveyMonkeyApplication.class
)
public class ApplicationIntegrationTest {

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ResponseRepository responseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    @Test
    @Transactional
    public void testEndToEndWorkflow() {
        Survey survey = createSurvey();
        addQuestionsToSurvey(survey);
        Response response = submitResponse(survey);
        verifySurveyResults(survey);
        closeSurvey(survey);
        cleanUp(survey, response);
    }

    private Survey createSurvey() {
        Survey survey = new Survey();
        survey.setTitle("Customer Satisfaction Survey");
        survey.setDescription("Gather feedback from customers.");
        survey.setIsOpen(true);
        return surveyRepository.save(survey);
    }

    private void addQuestionsToSurvey(Survey survey) {
        OpenEndedQuestion question1 = new OpenEndedQuestion();
        question1.setText("What did you like about our service?");
        question1.setSurvey(survey);

        NumericRangeQuestion question2 = new NumericRangeQuestion(1, 10);
        question2.setText("Rate your satisfaction on a scale of 1 to 10.");
        question2.setSurvey(survey);

        MultipleChoiceQuestion question3 = new MultipleChoiceQuestion();
        question3.setText("Which feature do you like the most?");
        Option option1 = new Option();
        option1.setText("Feature A");
        option1.setQuestion(question3);
        Option option2 = new Option();
        option2.setText("Feature B");
        option2.setQuestion(question3);
        question3.setOptions(Arrays.asList(option1, option2));
        question3.setSurvey(survey);

        questionRepository.saveAll(List.of(question1, question2, question3));
    }

    @Transactional
    private Response submitResponse(Survey survey) {
        Response response = new Response();
        response.setSurvey(survey);

        List<Question> questions = questionRepository.findAll();
        Answer answer1 = new Answer();
        answer1.setQuestion((OpenEndedQuestion) questions.get(0));
        answer1.setText("Excellent service!");
        answer1.setResponse(response);

        Answer answer2 = new Answer();
        answer2.setQuestion((NumericRangeQuestion) questions.get(1));
        answer2.setNumber(9);
        answer2.setResponse(response);

        Answer answer3 = new Answer();
        answer3.setQuestion((MultipleChoiceQuestion) questions.get(2));
        answer3.setSelectedOptionId(((MultipleChoiceQuestion) questions.get(2)).getOptions().get(0).getId());
        answer3.setResponse(response);

        response.setAnswers(Arrays.asList(answer1, answer2, answer3));
        return responseRepository.save(response);
    }

    private void verifySurveyResults(Survey survey) {
        List<Response> responses = responseRepository.findBySurvey(survey);
        assertEquals(1, responses.size());

        Response savedResponse = responses.get(0);
        assertEquals(3, savedResponse.getAnswers().size());

        // Validate individual answers
        Answer savedAnswer1 = savedResponse.getAnswers().get(0);
        assertEquals("Excellent service!", savedAnswer1.getText());

        Answer savedAnswer2 = savedResponse.getAnswers().get(1);
        assertEquals(9, savedAnswer2.getNumber());

        Answer savedAnswer3 = savedResponse.getAnswers().get(2);
        assertEquals(((MultipleChoiceQuestion) questionRepository.findAll().get(2)).getOptions().get(0).getId(), savedAnswer3.getSelectedOptionId());
    }

    private void closeSurvey(Survey survey) {
        survey.setIsOpen(false);
        surveyRepository.save(survey);

        Survey closedSurvey = surveyRepository.findById(survey.getId()).orElseThrow();
        assertFalse(closedSurvey.getIsOpen());
    }

    private void cleanUp(Survey survey, Response response) {
        responseRepository.deleteAll(responseRepository.findBySurvey(survey));
        questionRepository.deleteAll(survey.getQuestions());
        surveyRepository.delete(survey);
    }
}
