package com.group23.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group23.dto.SurveyResultDTO;
import com.group23.model.Survey;
import com.group23.model.User;
import com.group23.service.ResultService;
import com.group23.service.SurveyService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for SurveyorController.
 */
@WebMvcTest(SurveyorController.class)
public class SurveyorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SurveyService surveyService;

    @MockBean
    private ResultService resultService;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Test for creating a survey.

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("POST /admin/survey - Create Survey")
    public void testCreateSurvey() throws Exception {
        Survey survey = new Survey();
        survey.setId(1L);
        survey.setTitle("Test Survey");
        survey.setDescription("This is a test survey.");
        survey.setIsOpen(true);

        when(surveyService.createSurvey(any(Survey.class), any(User.class)))
                .thenReturn(survey);

        mockMvc.perform(post("/admin/survey")
                        .with(csrf()) // Include CSRF token
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(survey)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(survey.getId()))
                .andExpect(jsonPath("$.title").value(survey.getTitle()))
                .andExpect(jsonPath("$.description").value(survey.getDescription()))
                .andExpect(jsonPath("$.isOpen").value(survey.getIsOpen()));
    }*/

    /**
     * Test for getting survey responses.
     */
    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("GET /admin/survey/{id}/responses - Get Survey Responses")
    public void testGetResponses() throws Exception {
        Long surveyId = 1L;
        Survey survey = new Survey();
        survey.setId(surveyId);
        SurveyResultDTO surveyResultDTO = new SurveyResultDTO();

        when(surveyService.getSurveyById(surveyId)).thenReturn(survey);
        when(resultService.generateSurveyResult(survey)).thenReturn(surveyResultDTO);

        mockMvc.perform(get("/admin/survey/{id}/responses", surveyId))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(surveyResultDTO)));
    }

    /**
     * Test for deleting a survey.
     */
    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("POST /admin/survey/{id}/delete - Delete Survey")
    public void testDeleteSurvey() throws Exception {
        Long surveyId = 1L;

        // Adjust the mocking based on the method signature
        // Assuming deleteSurvey returns boolean
        when(surveyService.deleteSurvey(surveyId)).thenReturn(true);

        mockMvc.perform(post("/admin/survey/{id}/delete", surveyId)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/surveys"));
    }

    /**
     * Test for deleting selected surveys.
     */
    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("POST /admin/survey/delete - Delete Selected Surveys")
    public void testDeleteSelectedSurveys() throws Exception {
        List<Long> surveyIds = Arrays.asList(1L, 2L);

        when(surveyService.deleteSurvey(anyLong())).thenReturn(true);

        mockMvc.perform(post("/admin/survey/delete")
                        .with(csrf())
                        .param("surveyIds", "1", "2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/survey/"));
    }

    /**
     * Test for exporting surveys.
     */
    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("GET /admin/survey/export - Export Surveys")
    public void testExportSurveys() throws Exception {
        SurveyResultDTO surveyResultDTO = new SurveyResultDTO();
        List<SurveyResultDTO> surveys = Collections.singletonList(surveyResultDTO);

        when(surveyService.exportAllSurveys()).thenReturn(surveys);

        mockMvc.perform(get("/admin/survey/export"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", "attachment; filename=surveys.json"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(surveys)));
    }

    /**
     * Test for displaying the create survey form.
     */
    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("GET /admin/survey/create - Display Create Survey Form")
    public void testCreateSurveyForm() throws Exception {
        mockMvc.perform(get("/admin/survey/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("survey/create"))
                .andExpect(model().attributeExists("survey"));
    }

    /**
     * Test for creating a survey via form submission.
     */
    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("POST /admin/survey/create - Create Survey via Form")
    public void testCreateSurveyFormSubmission() throws Exception {
        Survey survey = new Survey();
        survey.setId(1L);
        survey.setTitle("Test Survey");
        survey.setDescription("This is a test survey.");
        survey.setIsOpen(true);

        doNothing().when(surveyService).saveSurvey(any(Survey.class));

        mockMvc.perform(post("/admin/survey/create")
                        .with(csrf())
                        .flashAttr("survey", survey))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/survey/"));
    }

    /**
     * Test for listing surveys.
     */
    @Test
    @WithMockUser
    @DisplayName("GET /admin/survey/ - List Surveys")
    public void testListSurveys() throws Exception {
        Survey survey = new Survey();
        survey.setId(1L);
        survey.setTitle("Test Survey");
        survey.setDescription("This is a test survey.");
        survey.setIsOpen(true);
        List<Survey> surveys = Collections.singletonList(survey);

        when(surveyService.listAllSurveys()).thenReturn(surveys);

        mockMvc.perform(get("/admin/survey/"))
                .andExpect(status().isOk())
                .andExpect(view().name("survey/list"))
                .andExpect(model().attributeExists("surveys"))
                .andExpect(model().attribute("surveys", surveys));
    }

    /**
     * Test for viewing a specific survey.
     */
    @Test
    @WithMockUser
    @DisplayName("GET /admin/survey/{id} - View Survey")
    public void testViewSurvey() throws Exception {
        Long surveyId = 1L;
        Survey survey = new Survey();
        survey.setId(surveyId);
        survey.setTitle("Test Survey");
        survey.setDescription("This is a test survey.");
        survey.setIsOpen(true);

        when(surveyService.getSurveyById(surveyId)).thenReturn(survey);

        mockMvc.perform(get("/admin/survey/{id}", surveyId))
                .andExpect(status().isOk())
                .andExpect(view().name("survey/view"))
                .andExpect(model().attributeExists("survey"))
                .andExpect(model().attribute("survey", survey))
                .andExpect(model().attribute("closed", false));
    }

    /**
     * Test for importing surveys.
     */
    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("POST /admin/survey/import - Import Surveys")
    public void testImportSurveys() throws Exception {
        SurveyResultDTO surveyResultDTO = new SurveyResultDTO();
        List<SurveyResultDTO> surveyResults = Collections.singletonList(surveyResultDTO);
        String jsonData = objectMapper.writeValueAsString(surveyResults);

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "surveys.json",
                MediaType.APPLICATION_JSON_VALUE,
                jsonData.getBytes()
        );

        doNothing().when(surveyService).importSurveys(any(List.class));

        mockMvc.perform(multipart("/admin/survey/import")
                        .file(file)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("Surveys imported successfully."));
    }

    /**
     * Test for closing a survey.
     */
    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("POST /admin/survey/{id}/close - Close Survey")
    public void testCloseSurvey() throws Exception {
        Long surveyId = 1L;

        doNothing().when(surveyService).closeSurvey(surveyId);

        mockMvc.perform(post("/admin/survey/{id}/close", surveyId)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/survey/" + surveyId + "?closed=true"));
    }
}
