package com.group23.model;

import java.util.List;
import java.util.Map;

/**
 * Represents the compiled results of a survey once it has been closed.
 */
public class SurveyResult {

    /**
     * Unique identifier for the survey result.
     */
    private Long id;

    /**
     * The survey the results belong to.
     */
    private Survey survey;

    /**
     * Map of open-ended questions to their answers.
     */
    private Map<Question, List<String>> openEndedResults;

    /**
     * Map of numeric questions to histograms (value to count).
     */
    private Map<Question, Map<Integer, Integer>> numericResults;

    /**
     * Map of multiple-choice questions to option counts.
     */
    private Map<Question, Map<Option, Integer>> choiceResults;

    // Getters and setters

    public Long getId() {
        return id;
    }

    // No setter for 'id' if it's auto-generated.

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    public Map<Question, List<String>> getOpenEndedResults() {
        return openEndedResults;
    }

    public void setOpenEndedResults(Map<Question, List<String>> openEndedResults) {
        this.openEndedResults = openEndedResults;
    }

    public Map<Question, Map<Integer, Integer>> getNumericResults() {
        return numericResults;
    }

    public void setNumericResults(Map<Question, Map<Integer, Integer>> numericResults) {
        this.numericResults = numericResults;
    }

    public Map<Question, Map<Option, Integer>> getChoiceResults() {
        return choiceResults;
    }

    public void setChoiceResults(Map<Question, Map<Option, Integer>> choiceResults) {
        this.choiceResults = choiceResults;
    }
}
