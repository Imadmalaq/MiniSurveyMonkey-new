package com.group23.util;

import com.group23.model.Option;
import com.group23.model.Question;

import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * Utility class for generating charts based on survey results.
 */
public class ChartGenerator {

    /**
     * Generates a histogram image for numeric range question results.
     *
     * @param numericData a map of numeric values to their counts
     * @param question    the numeric range question
     * @return a BufferedImage representing the histogram
     */
    public BufferedImage generateHistogram(Map<Integer, Integer> numericData, Question question) {
        // Method implementation goes here
        return null;
    }

    /**
     * Generates a pie chart image for multiple-choice question results.
     *
     * @param choiceData a map of options to their counts
     * @param question   the multiple-choice question
     * @return a BufferedImage representing the pie chart
     */
    public BufferedImage generatePieChart(Map<Option, Integer> choiceData, Question question) {
        // Method implementation goes here
        return null;
    }
}
