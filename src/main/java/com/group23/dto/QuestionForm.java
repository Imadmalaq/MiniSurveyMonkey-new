package com.group23.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Data Transfer Object for question form data.
 */
public class QuestionForm {

    private String text;
    private String type;
    private Integer minValue; // For NumericRangeQuestion
    private Integer maxValue; // For NumericRangeQuestion
    private String optionsAsString; // For MultipleChoiceQuestion

    // Getters and setters

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getMinValue() {
        return minValue;
    }

    public void setMinValue(Integer minValue) {
        this.minValue = minValue;
    }

    public Integer getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Integer maxValue) {
        this.maxValue = maxValue;
    }

    public String getOptionsAsString() {
        return optionsAsString;
    }

    public void setOptionsAsString(String optionsAsString) {
        this.optionsAsString = optionsAsString;
    }

    // Method to parse options into a list
    public List<String> getOptions() {
        if (optionsAsString != null && !optionsAsString.isEmpty()) {
            return Arrays.asList(optionsAsString.split("\\r?\\n"));
        }
        return new ArrayList<>();
    }
}
