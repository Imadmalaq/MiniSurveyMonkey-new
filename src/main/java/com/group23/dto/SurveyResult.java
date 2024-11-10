package com.group23.dto;

import com.group23.model.Survey;
import java.util.List;
import java.util.Map;

/**
 * Data Transfer Object (DTO) for encapsulating survey results.
 * This class is used to transfer survey results data from the backend to the
 * frontend, including open-ended, numeric range, and multiple-choice responses.
 * The survey results are organized in separate maps according to question types,
 * making it easy to access and display specific types of results.
 */
public class SurveyResult {

    private Survey survey; // The survey associated with these results

    private Map<Long, List<String>> openEndedResults; // Maps questionId to a list of responses for open-ended questions

    private Map<Long, Map<Integer, Integer>> numericResults; // Maps questionId to a map of numeric values and their count

    private Map<Long, Map<Long, Integer>> choiceResults; // Maps questionId to a map of optionIds and their count for multiple-choice questions



    /**
     * Gets the survey associated with these results.
     *
     * @return The Survey object associated with the results.
     */
    public Survey getSurvey() {
        return survey;
    }

    /**
     * Sets the survey associated with these results.
     *
     * @param survey The Survey object to set.
     */
    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    /**
     * Gets the open-ended question results.
     * Each entry in the map contains a question ID mapped to a list of text responses.
     * @return A map with question IDs as keys and lists of responses as values.
     */
    public Map<Long, List<String>> getOpenEndedResults() {
        return openEndedResults;
    }

    /**
     * Sets the open-ended question results.
     *
     * @param openEndedResults A map where each key is a question ID and each value is a list of responses.
     */
    public void setOpenEndedResults(Map<Long, List<String>> openEndedResults) {
        this.openEndedResults = openEndedResults;
    }

    /**
     * Gets the numeric question results.
     * Each entry in the map contains a question ID mapped to a nested map. The nested
     * map has numeric values as keys and their respective counts as values.
     *
     * @return A map with question IDs as keys and maps of numeric values and their counts as values.
     */
    public Map<Long, Map<Integer, Integer>> getNumericResults() {
        return numericResults;
    }

    /**
     * Sets the numeric question results.
     *
     * @param numericResults A map where each key is a question ID, and each value
     *                       is a nested map with numeric values and their counts.
     */
    public void setNumericResults(Map<Long, Map<Integer, Integer>> numericResults) {
        this.numericResults = numericResults;
    }

    /**
     * Gets the multiple-choice question results.
     * Each entry in the map contains a question ID mapped to a nested map. The nested
     * map has option IDs as keys and their respective counts as values.
     *
     * @return A map with question IDs as keys and maps of option IDs and their counts as values.
     */
    public Map<Long, Map<Long, Integer>> getChoiceResults() {
        return choiceResults;
    }

    /**
     * Sets the multiple-choice question results.
     *
     * @param choiceResults A map where each key is a question ID, and each value
     *                      is a nested map with option IDs and their counts.
     */
    public void setChoiceResults(Map<Long, Map<Long, Integer>> choiceResults) {

        this.choiceResults = choiceResults;
    }
}
