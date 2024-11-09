package com.group23.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents the compiled results of a survey once it has been closed.
 *
 * <p>The {@code SurveyResult} class encapsulates the aggregated responses collected from participants
 * after a survey has been concluded. It organizes the data into various categories based on the
 * type of questions asked in the survey, including open-ended, numeric range, and multiple-choice questions.</p>
 *
 * <p>This class is not marked as an {@code @Entity} because it represents a compiled result rather
 * than a persistent entity in the database. Instead, it serves as a data transfer object (DTO) to
 * facilitate the processing and presentation of survey results.</p>
 *
 * <p><strong>Key Features:</strong></p>
 * <ul>
 *     <li><strong>Open-Ended Results:</strong> Stores lists of textual answers provided by respondents.</li>
 *     <li><strong>Numeric Results:</strong> Maintains histograms for numeric range questions, mapping
 *     each possible value to the number of respondents who selected it.</li>
 *     <li><strong>Choice Results:</strong> Aggregates counts of selected options for multiple-choice questions.</li>
 * </ul>
 *
 *
 * @author Imad Mohamed
 * @version 1.0
 */
public class SurveyResult {

    /**
     * Unique identifier for the survey result.
     *
     * <p>This field serves as the primary key for the {@code SurveyResult} and is used to uniquely
     * identify each set of survey results within the system.</p>
     */
    private Long id;

    /**
     * The survey the results belong to.
     *
     * <p>This field references the {@link Survey} entity associated with these results, establishing
     * a relationship between the survey and its compiled results.</p>
     */
    private Survey survey;

    /**
     * Map of open-ended questions to their answers.
     *
     * <p>This map associates each open-ended {@link Question} with a list of {@code String} answers
     * provided by respondents. It allows for the storage and retrieval of qualitative feedback.</p>
     *
     * <p><strong>Example:</strong> For a question like "What did you like about our service?",
     * the map would contain the question as the key and a list of textual responses as the value.</p>
     */
    private Map<Question, List<String>> openEndedResults;

    /**
     * Map of numeric questions to histograms (value to count).
     *
     * <p>This map links each numeric {@link Question} to a histogram represented by another map.
     * The inner map maps each possible numeric value to the number of respondents who selected that value.
     * This structure facilitates the analysis of quantitative data by showing distribution patterns.</p>
     *
     * <p><strong>Example:</strong> For a rating question like "Rate your satisfaction on a scale of 1 to 5",
     * the map might associate the question with a histogram where 1 maps to 2 respondents,
     * 2 maps to 5 respondents, and so on.</p>
     */
    private Map<Question, Map<Integer, Integer>> numericResults;

    /**
     * Map of multiple-choice questions to option counts.
     *
     * <p>This map connects each multiple-choice {@link Question} with a nested map that tracks
     * how many respondents selected each {@link Option}. The outer map uses the question as the key,
     * while the inner map uses the option as the key and the count of selections as the value.</p>
     *
     * <p><strong>Example:</strong> For a question like "Which feature do you use the most?",
     * the map would link the question to a map where "Feature A" maps to 10 respondents,
     * "Feature B" maps to 15 respondents, etc.</p>
     */
    private Map<Question, Map<Option, Integer>> choiceResults;

    // Getters and setters

    /**
     * Retrieves the unique identifier of this survey result.
     *
     * @return the {@code id} of the survey result
     */
    public Long getId() {
        return id;
    }

    /**
     * Retrieves the survey associated with this result.
     *
     * @return the {@code survey} to which these results belong
     */
    public Survey getSurvey() {
        return survey;
    }

    /**
     * Associates this survey result with a specific survey.
     *
     * <p>By setting the {@code survey}, you link these results to the corresponding survey, establishing
     * the context in which the responses were collected.</p>
     *
     * @param survey the {@code Survey} to associate with this survey result
     */
    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    /**
     * Retrieves the map of open-ended questions to their answers.
     *
     * @return a {@code Map} where each key is an open-ended {@link Question} and the value is a list of
     *         {@code String} answers provided by respondents
     */
    public Map<Question, List<String>> getOpenEndedResults() {
        return openEndedResults;
    }

    /**
     * Sets the map of open-ended questions to their answers.
     *
     * <p>By setting the {@code openEndedResults}, you define the collection of qualitative responses
     * associated with each open-ended question in the survey.</p>
     *
     * @param openEndedResults a {@code Map} mapping open-ended {@link Question}s to lists of {@code String} answers
     */
    public void setOpenEndedResults(Map<Question, List<String>> openEndedResults) {
        this.openEndedResults = openEndedResults;
    }

    /**
     * Retrieves the map of numeric questions to histograms.
     *
     * @return a {@code Map} where each key is a numeric {@link Question} and the value is another map
     *         representing the histogram of responses, mapping each numeric value to its count
     */
    public Map<Question, Map<Integer, Integer>> getNumericResults() {
        return numericResults;
    }

    /**
     * Sets the map of numeric questions to histograms.
     *
     * <p>By setting the {@code numericResults}, you define the distribution of numeric responses
     * for each numeric question in the survey.</p>
     *
     * @param numericResults a {@code Map} mapping numeric {@link Question}s to their corresponding histograms
     */
    public void setNumericResults(Map<Question, Map<Integer, Integer>> numericResults) {
        this.numericResults = numericResults;
    }

    /**
     * Retrieves the map of multiple-choice questions to option counts.
     *
     * @return a {@code Map} where each key is a multiple-choice {@link Question} and the value is another map
     *         that associates each {@link Option} with the number of times it was selected by respondents
     */
    public Map<Question, Map<Option, Integer>> getChoiceResults() {
        return choiceResults;
    }

    /**
     * Sets the map of multiple-choice questions to option counts.
     *
     * <p>By setting the {@code choiceResults}, you define how many respondents selected each option
     * for every multiple-choice question in the survey.</p>
     *
     * @param choiceResults a {@code Map} mapping multiple-choice {@link Question}s to their corresponding
     *                      {@link Option} selection counts
     */
    public void setChoiceResults(Map<Question, Map<Option, Integer>> choiceResults) {
        this.choiceResults = choiceResults;
    }
}
