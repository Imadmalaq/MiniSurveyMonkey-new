package com.group23.dto;

import com.group23.model.Survey;

import java.util.List;
import java.util.Map;

/**
 * Data Transfer Object for survey results.
 */
public class SurveyResult {

    private Survey survey;

    private Map<Long, List<String>> openEndedResults; // questionId -> list of responses

    private Map<Long, Map<Integer, Integer>> numericResults; // questionId -> (value -> count)

    private Map<Long, Map<Long, Integer>> choiceResults; // questionId -> (optionId -> count)

    // Getters and setters

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
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
