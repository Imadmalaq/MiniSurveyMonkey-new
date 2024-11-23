package com.group23.dto;

import com.group23.model.Answer;
import com.group23.model.Survey;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Data Transfer Object (DTO) for encapsulating survey details and results.
 * Used for export/import functionality or transferring survey data.
 */
public class SurveyResultDTO {

    private Survey survey;

    private Long surveyId;
    private String title;
    private String description;
    private boolean isOpen;
    private List<QuestionForm> questions; // Represents the list of questions in the survey

    private Map<Long, List<String>> openEndedResults; // Responses for open-ended questions
    private Map<Long, Map<Integer, Integer>> numericResults; // Numeric range question results

    private Map<Long, Map<Long, Integer>> choiceResults;

    private Map<Long, List<Answer>> answers; // Question ID to List of Answers
    // Multiple-choice results as text

    // Getters and Setters
    public SurveyResultDTO() {
        this.openEndedResults = new HashMap<>();
        this.numericResults = new HashMap<>();
        this.choiceResults = new HashMap<>();
    }


    public Survey getSurvey(){
        return survey;
    }
    public void setSurvey(Survey survey){
        this.survey = survey;
    }

    public Long getSurveyId() {
        return surveyId;
    }

    public Map<Long, List<Answer>> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<Long, List<Answer>> answers) {
        this.answers = answers;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public List<QuestionForm> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionForm> questions) {
        this.questions = questions;
    }

    public Map<Long, List<String>> getOpenEndedResults() {
        return openEndedResults;
    }

    public void setOpenEndedResults(Map<Long, List<String>> openEndedResults) {
        this.openEndedResults = openEndedResults;
    }

    public Map<Long, Map<Integer, Integer>> getNumericResults() {
        return numericResults;
    }

    public void setNumericResults(Map<Long, Map<Integer, Integer>> numericResults) {
        this.numericResults = numericResults;
    }

    public Map<Long, Map<Long, Integer>> getChoiceResults() {
        return choiceResults;
    }

    public void setChoiceResults(Map<Long, Map<Long, Integer>> choiceResults) {
        this.choiceResults = choiceResults;
    }
}
