package com.group23.model;

import jakarta.persistence.Entity;

/**
 * Represents an open-ended question within a survey where respondents can provide
 * free-form text answers. This type of question allows participants to express their
 * thoughts, opinions, and detailed feedback without being constrained by predefined
 * options.
 *
 * <p>The {@code OpenEndedQuestion} class extends the {@link Question} class, inheriting
 * common attributes such as the question text and identifier. It is marked as a JPA
 * entity, enabling it to be persisted in the database. Since open-ended questions do
 * not require additional fields beyond those provided by the base {@code Question} class,
 * this class serves primarily to differentiate the type of question within the application's
 * domain model.</p>
 *
 *
 * @author Imad Mohamed
 * @version 1.0
 */
@Entity
public class OpenEndedQuestion extends Question {
    // No additional fields

    // Getters and setters
}
