package com.group23.model;

import jakarta.persistence.Entity;

/**
 * Represents a numeric range question.
 */
@Entity
public class NumericRangeQuestion extends Question {

    private Integer minValue;
    private Integer maxValue;

    // Getters and setters

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
}
