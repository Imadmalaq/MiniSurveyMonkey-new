package com.group23.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group23.model.Answer;
import com.group23.model.Response;
import com.group23.model.Survey;
import com.group23.model.User;
import com.group23.service.ResponseService;
import com.group23.service.UserService;
import com.group23.service.SurveyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


class UserControllerTest {
    /**
     * Instance of the UserController.
     * It allows to automatically inject mocked dependencies into the field.
     */
    @InjectMocks
    private UserController userController;

    /**
     * Instance of SurveyService.
     * Allows to create a mock of SurveyService and allows
     * to isolate the controller from the actual service layer.
     */
    @Mock
    private SurveyService surveyService;

    /**
     * The Model is used to pass data from the controller to the view.
     * It is populated with attributes to test whether controller
     * behaves correctly.
     */
    private Model model;

    @Mock
    private ObjectMapper objectMapper;


    @Mock
    private UserService userService;

    @Mock
    private ResponseService responseService;

    /**
     * Ensures that the setup runs before every test method,
     * and initializes new instances everytime to avoid dependencies.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);   //Allows to write the test without manually setting up mocks and dependencies - mocks SurveyController and SurveyService
        model = new BindingAwareModelMap();          // Handles model in controller classes and creates new instances of model for each method to execute

    }

    @Test
    public void loginPageTest() {
        String viewName = userController.loginPage();
        assertEquals("auth/login", viewName);                   // Check if the login page view is returned
    }

    @Test
    public void registerPageTest() {
        String viewName = userController.registerPage(model);
        assertEquals("auth/register", viewName);                // Check if the registration page view is returned
        assertTrue(model.containsAttribute("user"));        // Checks the model contains a "user" attribute
    }

    @Test
    public void getActiveSurveysTest() {
        List<Survey> mockSurveys = Arrays.asList(new Survey(), new Survey());
        when(surveyService.listAllSurveys()).thenReturn(mockSurveys);

        ResponseEntity<List<Survey>> response = userController.getActiveSurveys();
        assertEquals(200, response.getStatusCodeValue());        // Checks the response status
        assertEquals(mockSurveys, response.getBody());                  // Checks the surveys are returned
    }


    @Test
    public void testSubmitResponse() {
        Long surveyId = 1L;
        List<Answer> answers = Arrays.asList(new Answer(), new Answer());
        Response response = new Response();

        when(responseService.saveResponse(any(Response.class))).thenReturn(response);  // Mocking the service behavior
        ResponseEntity<Response> result = userController.submitResponse(surveyId, answers, null, response);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());   // Checks the response status
        assertEquals(response, result.getBody());                   // Checks that returned response matches the mock
    }

    @Test
    public void customLogin_AdminTest() {
        String username = "admin";
        String password = "adminPassword";

        when(userService.validateAdminCredentials(username, password)).thenReturn(true);  // Mocks the behavior to return true

        String viewName = userController.customLogin(username, password);
        assertEquals("redirect:/admin/survey", viewName);           // Redirect to admin page for admin credentials
    }

    @Test
    public void customLogin_FailureTest() {
        String username = "user";
        String password = "wrongPassword";

        when(userService.validateAdminCredentials(username, password)).thenReturn(false);  // Mocks the behavior to return false

        String viewName = userController.customLogin(username, password);
        assertEquals("redirect:/login?error=true", viewName); // Redirect back to login page with error
    }

    @Test
    public void testListSurveys() {
        List<Survey> surveys = Arrays.asList(new Survey(), new Survey());
        when(surveyService.listAllSurveys()).thenReturn(surveys);

        String viewName = userController.listSurveys(model);

        assertEquals("home/home", viewName);
        assertEquals(surveys, model.getAttribute("surveys"));       // Checks surveys are added to the model
    }

}