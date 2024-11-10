package com.group23.model;

import jakarta.persistence.Entity;

/**
 * Represents a numeric range question within a survey.
 * This entity extends the {@link Question} class, inheriting common question attributes
 * while adding specific functionalities related to numeric range constraints.
 *
 * <p>Each {@code NumericRangeQuestion} defines a range of acceptable numeric values
 * that respondents can provide as their answer. This is typically used for questions
 * that require quantitative responses, such as rating scales or quantity inputs.</p>
 *
 * <p>This entity is managed by JPA and maps to a corresponding table in the database,
 * enabling CRUD operations and relational mappings. By extending {@code Question},
 * it leverages inheritance to reuse common question-related properties and behaviors.</p>
 *
 * @author Imad Mohamed
 * @version 1.0
 */
@Entity
public class NumericRangeQuestion extends Question {

    /**
     * The minimum acceptable value for this numeric range question.
     * Respondents should provide a number that is equal to or greater than this value.
     *
     * <p>For example, if the question is "Rate your satisfaction on a scale of 1 to 10",
     * the {@code minValue} would be set to {@code 1}.</p>
     */
    private Integer minValue;

    /**
     * The maximum acceptable value for this numeric range question.
     * Respondents should provide a number that is equal to or less than this value.
     *
     * <p>Continuing the previous example, the {@code maxValue} would be set to {@code 10}.</p>
     */
    private Integer maxValue;

    // Constructors

    /**
     * Default constructor for {@code NumericRangeQuestion}.
     * Initializes a new instance of the {@code NumericRangeQuestion} class.
     */
    public NumericRangeQuestion() {
        super();
    }

    /**
     * Parameterized constructor for {@code NumericRangeQuestion}.
     *
     * @param minValue the minimum acceptable value for the question
     * @param maxValue the maximum acceptable value for the question
     */
    public NumericRangeQuestion(Integer minValue, Integer maxValue) {
        super();
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    // Getters and Setters

    /**
     * Retrieves the minimum acceptable value for this numeric range question.
     *
     * @return the {@code minValue} indicating the lower bound of the acceptable range
     */
    public Integer getMinValue() {
        return minValue;
    }

    /**
     * Sets the minimum acceptable value for this numeric range question.
     *
     * <p>By setting the {@code minValue}, you define the lower bound that respondents
     * must adhere to when providing their numeric answer.</p>
     *
     * @param minValue the {@code minValue} to set as the lower bound
     */
    public void setMinValue(Integer minValue) {
        this.minValue = minValue;
    }

    /**
     * Retrieves the maximum acceptable value for this numeric range question.
     *
     * @return the {@code maxValue} indicating the upper bound of the acceptable range
     */
    public Integer getMaxValue() {
        return maxValue;
    }

    /**
     * Sets the maximum acceptable value for this numeric range question.
     *
     * <p>By setting the {@code maxValue}, you define the upper bound that respondents
     * must adhere to when providing their numeric answer.</p>
     *
     * @param maxValue the {@code maxValue} to set as the upper bound
     */
    public void setMaxValue(Integer maxValue) {
        this.maxValue = maxValue;
    }

    /**
     * Validates whether a given numeric value falls within the defined range.
     *
     * <p>This method checks if the provided {@code value} is greater than or equal to
     * {@code minValue} and less than or equal to {@code maxValue}.</p>
     *
     * @param value the numeric value to validate
     * @return {@code true} if the value is within range; {@code false} otherwise
     */
    public boolean isValidValue(Integer value) {
        if (value == null) {
            return false;
        }
        return value >= minValue && value <= maxValue;
    }
}
