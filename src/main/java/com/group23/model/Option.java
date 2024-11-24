package com.group23.model;

import jakarta.persistence.*;

/**
 * Represents an option for a multiple-choice question within a survey.
 *
 * <p>This entity defines the possible choices that a respondent can select for a
 * multiple-choice question. Each {@code Option} is associated with a specific
 * {@link MultipleChoiceQuestion}, allowing for the customization of selectable answers.</p>
 *
 * <p>The {@code Option} class includes an auto-generated unique identifier and the text
 * content of the option. It is managed by JPA and maps to a corresponding table in the
 * database, facilitating CRUD operations and relational mappings.</p>
 *
 *
 * @author Imad Mohamed
 * @version 1.0
 */
@Entity
public class Option {

    /**
     * Default constructor required by JPA.
     */
    public Option() {
    }


    public void setId(Long id) {
        this.id = id;
    }

    /**
     * The unique identifier for this option.
     * It is automatically generated by the database upon creation.
     *
     * @see GenerationType#IDENTITY
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The text content of this option.
     * This represents the choice presented to survey respondents for a multiple-choice question.
     *
     * <p>For example, in a question like "What is your favorite color?", the options might be
     * "Red", "Blue", "Green", etc., where each option's {@code text} field holds one of these values.</p>
     */
    private String text;

    /**
     * Retrieves the unique identifier of this option.
     *
     * @return the {@code id} of the option
     */
    public Long getId() {
        return id;
    }

    /**
     * Retrieves the text content of this option.
     *
     * @return the {@code text} of the option
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text content of this option.
     *
     * <p>By setting the {@code text}, you define the display text that will be shown to survey
     * participants as a selectable option for the associated multiple-choice question.</p>
     *
     * @param text the {@code text} to set for this option
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Provides a string representation of the {@code Option} instance.
     *
     * @return a string containing the class name and its properties
     */
    @Override
    public String toString() {
        return "Option{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }

    /**
     * Compares this {@code Option} with another object for equality.
     *
     * <p>Two {@code Option} instances are considered equal if they have the same
     * {@code id} and {@code text} content.</p>
     *
     * @param o the object to compare with this {@code Option}
     * @return {@code true} if the objects are equal; {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Option option = (Option) o;

        if (!id.equals(option.id)) return false;
        return text.equals(option.text);
    }

    /**
     * Computes the hash code for this {@code Option}.
     *
     * @return an integer hash code value for this object
     */
    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + text.hashCode();
        return result;
    }
}
