package com.group23.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Data Transfer Object (DTO) for encapsulating question form data.
 * This class is used to transfer question data between the user interface
 * and the backend, especially for creating or updating questions in a form.
 * It includes fields for different types of questions, such as text for the
 * question, type, numeric range values, and options for multiple-choice questions.
 */
public class QuestionForm {

    private String text;
    private String type;
    private Integer minValue;
    private Integer maxValue;
    private String optionsAsString;

    private Long id;

    private List<String> options;



    /**
     * Gets the text of the question.
     *
     * @return The question text.
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text of the question.
     *
     * @param text The question text.
     */
    public void setText(String text) {
        this.text = text;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }



    /**
     * Gets the type of the question.
     *
     * @return The question type as a String.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the question (e.g., "OpenEnded", "NumericRange", "MultipleChoice").
     *
     * @param type The question type.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the minimum value for a numeric range question.
     *
     * @return The minimum value, or null if not applicable.
     */
    public Integer getMinValue() {
        return minValue;
    }

    /**
     * Sets the minimum value for a numeric range question.
     *
     * @param minValue The minimum value for the numeric range.
     */
    public void setMinValue(Integer minValue) {
        this.minValue = minValue;
    }

    /**
     * Gets the maximum value for a numeric range question.
     *
     * @return The maximum value, or null if not applicable.
     */
    public Integer getMaxValue() {
        return maxValue;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    /**
     * Sets the maximum value for a numeric range question.
     *
     * @param maxValue The maximum value for the numeric range.
     */
    public void setMaxValue(Integer maxValue) {
        this.maxValue = maxValue;
    }

    /**
     * Gets the options for a multiple-choice question as a single string.
     *
     * @return The options in a single string, separated by line breaks.
     */
    public String getOptionsAsString() {
        return optionsAsString;
    }

    /**
     * Sets the options for a multiple-choice question in a single string format.
     *
     * @param optionsAsString The options, with each option on a new line.
     */
    public void setOptionsAsString(String optionsAsString) {
        this.optionsAsString = optionsAsString;
    }

    /**
     * Parses the options string into a list of individual options.
     * This method splits the options string by line breaks to create a list
     * of options, useful for processing multiple-choice question options.
     *
     * @return A List of options as strings. Returns an empty list if no options are set.
     */
    public List<String> getOptions() {
        if (optionsAsString != null && !optionsAsString.isEmpty()) {
            return Arrays.asList(optionsAsString.split("\\r?\\n"));
        }
        return new ArrayList<>();
    }
}
