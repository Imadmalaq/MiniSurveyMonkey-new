package com.group23.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a multiple-choice question within a survey.
 * This entity extends the {@link Question} class, inheriting common question attributes
 * while adding specific functionalities related to multiple-choice options.
 *
 * <p>Each {@code MultipleChoiceQuestion} contains a list of {@link Option} entities,
 * representing the possible answers that respondents can select from. The relationship
 * between {@code MultipleChoiceQuestion} and {@code Option} is one-to-many, ensuring
 * that each question can have multiple associated options.</p>
 *
 * <p>This entity is managed by JPA and maps to a corresponding table in the database,
 * enabling CRUD operations and relational mappings. The cascading and orphan removal
 * settings ensure that options are appropriately managed alongside their parent question.</p>
 *
 * @author Imad Mohamed
 * @version 1.0
 */
@Entity
public class MultipleChoiceQuestion extends Question {

    /**
     * The list of options available for this multiple-choice question.
     * This establishes a one-to-many relationship between {@code MultipleChoiceQuestion}
     * and {@code Option}, indicating that a single question can have multiple options.
     *
     * <p>The {@code cascade = CascadeType.ALL} setting ensures that all persistence operations
     * (e.g., persist, merge, remove) applied to the question are cascaded to its options.
     * The {@code orphanRemoval = true} setting ensures that any option removed from the
     * list is also removed from the database, maintaining referential integrity.</p>
     *
     * @see CascadeType#ALL
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Option> options = new ArrayList<>();

    /**
     * Retrieves the list of options associated with this multiple-choice question.
     *
     * @return a {@code List} of {@link Option} entities representing the possible answers
     *         for this question
     */
    public List<Option> getOptions() {
        return options;
    }

    /**
     * Sets the list of options for this multiple-choice question.
     *
     * <p>By setting the options, you define the possible answers that respondents can select
     * from when answering this question. This method replaces the current list of options
     * with the provided list.</p>
     *
     * @param options a {@code List} of {@link Option} entities to associate with this question
     */
    public void setOptions(List<Option> options) {
        this.options = options;
    }
}
