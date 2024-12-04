package com.group23.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.group23.dto.SurveyResultDTO;
import com.group23.model.Survey;
import com.group23.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

/**
 * Controller for managing surveys.
 */
@Controller
@RequestMapping("/surveys")
public class SurveyController {

//    private final SurveyService surveyService;
//
//    @Autowired
//    public SurveyController(SurveyService surveyService) {
//        this.surveyService = surveyService;
//    }
//
//    /**
//     * Displays a list of all surveys.
//     *
//     * @param model the model to add attributes to
//     * @return the view name to render
//     */
//    @GetMapping
//    public String listSurveys(Model model) {
//        List<Survey> surveys = surveyService.listAllSurveys();
//        model.addAttribute("surveys", surveys);
//        return "survey/list";
//    }
//
//    /**
//     * Shows the form to create a new survey.
//     *
//     * @param model the model to add attributes to
//     * @return the view name to render
//     */
//    @GetMapping("/new")
//    public String showCreateSurveyForm(Model model) {
//        model.addAttribute("survey", new Survey());
//        return "survey/create";
//    }
//
//    /**
//     * Handles the submission of a new survey.
//     *
//     * @param survey the survey object populated from the form
//     * @return a redirect to the survey list page
//     */
////    @PostMapping
////    public String createSurvey(@ModelAttribute Survey survey) {
////        survey.setIsOpen(true); // Set survey to open by default
////        surveyService.createSurvey(survey);
////        return "redirect:/surveys";
////    }
//
//    /**
//     * Displays the details of a specific survey.
//     *
//     * @param id     the ID of the survey
//     * @param closed optional query parameter indicating if the survey was just closed
//     * @param model  the model to add attributes to
//     * @return the view name to render
//     */
//    @GetMapping("/{id}")
//    public String viewSurvey(@PathVariable Long id,
//                             @RequestParam(value = "closed", required = false) String closed,
//                             Model model) {
//        Survey survey = surveyService.getSurveyById(id);
//        if (survey == null) {
//            return "redirect:/surveys"; // Redirect if survey not found
//        }
//        model.addAttribute("survey", survey);
//        model.addAttribute("closed", closed != null);
//        return "survey/view";
//    }
//
//    /**
//     * Closes a survey, preventing further responses.
//     *
//     * @param id the ID of the survey to close
//     * @return a redirect to the survey details page with a closed parameter
//     */
//    @PostMapping("/{id}/close")
//    public String closeSurvey(@PathVariable Long id) {
//        surveyService.closeSurvey(id);
//        return "redirect:/surveys/" + id + "?closed=true";
//    }
//
//
//    //NEW methods to delete the survey
//
//    /**
//     * Deletes a specific survey by its ID.
//     *
//     * @param id the ID of the survey to delete
//     * @return a redirect to the survey list page
//     */
//    @PostMapping("/{id}/delete")
//    public String deleteSurvey(@PathVariable Long id, RedirectAttributes redirectAttributes) {
//        try {
//            surveyService.deleteSurvey(id);
//            redirectAttributes.addFlashAttribute("message", "Survey deleted successfully.");
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete survey. It may have associated data.");
//        }
//        return "redirect:/surveys";
//    }
//
//
//    /**
//     * Deletes multiple selected surveys.
//     *
//     * @param surveyIds list of survey IDs to delete
//     * @return a redirect to the survey list page
//     */
//    @PostMapping("/delete")
//    public String deleteSelectedSurveys(@RequestParam("surveyIds") List<Long> surveyIds) {
//        for (Long id : surveyIds) {
//            surveyService.deleteSurvey(id);
//        }
//        return "redirect:/surveys";
//    }
//
//    @GetMapping("/export")
//    public ResponseEntity<ByteArrayResource> exportSurveys() throws IOException {
//        List<SurveyResultDTO> surveys = surveyService.exportAllSurveys();
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonData = mapper.writeValueAsString(surveys);
//        ByteArrayResource resource = new ByteArrayResource(jsonData.getBytes());
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=surveys.json")
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(resource);
//
//    }
//
//    @PostMapping("/import")
//    public ResponseEntity<String> importSurveys(@RequestParam("file") MultipartFile file) throws IOException {
//        ObjectMapper mapper = new ObjectMapper();
//        List<SurveyResultDTO> surveyResults = mapper.readValue(file.getInputStream(), new TypeReference<>() {});
//        surveyService.importSurveys(surveyResults);
//        return ResponseEntity.ok("Surveys imported successfully.");
//    }
}
