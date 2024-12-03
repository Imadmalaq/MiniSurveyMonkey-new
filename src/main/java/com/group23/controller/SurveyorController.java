package com.group23.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.beans.factory.FactoryBean;

import org.springframework.stereotype.Service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.group23.dto.SurveyResultDTO;
import com.group23.model.Survey;
import com.group23.model.User;
import com.group23.service.ResultService;
import com.group23.service.SurveyService;


@Controller
@RequestMapping("/admin/survey")
public class SurveyorController {

    private final ResultService resultService;
    private SurveyService surveyService;
    private Model model;

    @Autowired
    public SurveyorController(SurveyService surveyService, ResultService resultService) {
        this.surveyService = surveyService;
        this.resultService = resultService;
    }


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Survey> createSurvey(@RequestBody Survey survey,
                                               @AuthenticationPrincipal User admin) throws Exception {
        Survey created = surveyService.createSurvey(survey, admin);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}/responses")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SurveyResultDTO> getResponses(@PathVariable Long id) {
        Survey survey = surveyService.getSurveyById(id);
        SurveyResultDTO responses = resultService.generateSurveyResult(survey);
        return ResponseEntity.ok(responses);
    }

    //NEW methods to delete the survey

    /**
     * Deletes a specific survey by its ID.
     *
     * @param id the ID of the survey to delete
     * @return a redirect to the survey list page
     */
    @PostMapping("/{id}/delete")
    public String deleteSurvey(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            surveyService.deleteSurvey(id);
            redirectAttributes.addFlashAttribute("message", "Survey deleted successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete survey. It may have associated data.");
        }
        return "redirect:/surveys";
    }


    /**
     * Deletes multiple selected surveys.
     *
     * @param surveyIds list of survey IDs to delete
     * @return a redirect to the survey list page
     */
    @PostMapping("/delete")
    public String deleteSelectedSurveys(@RequestParam("surveyIds") List<Long> surveyIds) {
        for (Long id : surveyIds) {
            surveyService.deleteSurvey(id);
        }
        return "redirect:/admin/survey/";
    }

    @GetMapping("/export")
    public ResponseEntity<ByteArrayResource> exportSurveys() throws IOException {
        List<SurveyResultDTO> surveys = surveyService.exportAllSurveys();
        ObjectMapper mapper = new ObjectMapper();
        String jsonData = mapper.writeValueAsString(surveys);
        ByteArrayResource resource = new ByteArrayResource(jsonData.getBytes());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=surveys.json")
                .contentType(MediaType.APPLICATION_JSON)
                .body(resource);

    }



    // ------------------------------------------
//    @GetMapping("/")
//    @PreAuthorize("hasRole('ADMIN')")
//    public String adminPage(Model model) {
//     System.out.println("hjkschsadkjhc");
//       List<Survey> surveys = surveyService.listAllSurveys();
//           model.addAttribute("surveys", surveys);
//        return "admin/home"; // Ensure this view exists.
//    }
//

    @GetMapping("/create")
    public String createSurveyForm(Model model) {
        model.addAttribute("survey", new Survey());
        return "survey/create";            // Return createSurvey.html view.
    }

    @PostMapping("/create")
    public String createSurvey(@ModelAttribute Survey survey) {

        survey.setIsOpen(true);
        surveyService.saveSurvey(survey);
        return "redirect:/admin/survey/"; // Redirect to admin page after saving.
    }

    /**
     * Displays a list of all surveys.
     *
     * @param model the model to add attributes to
     * @return the view name to render
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/")
    public String listSurveys(Model model) {
        List<Survey> surveys = surveyService.listAllSurveys();
        surveys.forEach(survey->System.out.println(survey+"survey"));

        model.addAttribute("surveys", surveys);
        return "survey/list";
    }

    /**
     * Displays the details of a specific survey.
     *
     * @param id     the ID of the survey
     * @param closed optional query parameter indicating if the survey was just closed
     * @param model  the model to add attributes to
     * @return the view name to render
     */
    @GetMapping("/{id}")
    public String viewSurvey(@PathVariable Long id,
                             @RequestParam(value = "open", required = false) String closed,
                             Model model) {

        Survey survey = surveyService.getSurveyById(id);
        if (survey == null) {
            return "redirect:/admin/survey"; // Redirect if survey not found
        }
        model.addAttribute("survey", survey);
        model.addAttribute("closed", closed != null);

        return "survey/view";
    }



    @PostMapping("/import")
    public ResponseEntity<String> importSurveys(@RequestParam("file") MultipartFile file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<SurveyResultDTO> surveyResults = mapper.readValue(file.getInputStream(), new TypeReference<>() {});
        surveyService.importSurveys(surveyResults);
        return ResponseEntity.ok("Surveys imported successfully.");
    }

    /**
     * Closes a survey, preventing further responses.
     *
     * @param id the ID of the survey to close
     * @return a redirect to the survey details page with a closed parameter
     */
    @PostMapping("/{id}/close")
    public String closeSurvey(@PathVariable Long id) {
        surveyService.closeSurvey(id);
        return "redirect:/admin/survey/" + id + "?closed=true";
    }

}