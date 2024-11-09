package com.group23.model;

import jakarta.persistence.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a user's response to a survey.
 *
 * <p>The {@code Response} class captures the answers provided by a respondent for a specific
 * {@link Survey}. It maintains a mapping between question identifiers and the corresponding
 * {@link Answer} entities, allowing for efficient retrieval and association of responses to
 * their respective questions.</p>
 *
 * <p>This entity is managed by JPA and maps to a corresponding table in the database,
 * enabling CRUD operations and relational mappings. The use of {@code @OneToMany} with a
 * {@code Map} facilitates the association between questions and answers, ensuring that each
 * answer is linked to its corresponding question within the survey.</p>
 *
 *
 * @author Imad Mohamed
 * @version 1.0
 */
@Entity
public class Response {

    /**
     * Unique identifier for the response.
     *
     * <p>This field serves as the primary key for the {@code Response} entity and is
     * automatically generated by the database upon insertion. It uniquely distinguishes
     * each response within the survey system.</p>
     *
     * @see GenerationType#IDENTITY
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The survey to which this response belongs.
     *
     * <p>This field establishes a many-to-one relationship between {@code Response} and {@code Survey},
     * indicating that multiple responses can be associated with a single survey. It facilitates
     * the organization and retrieval of responses within the context of their respective surveys.</p>
     */
    @ManyToOne
    private Survey survey;

    /**
     * A mapping of question identifiers to the corresponding answers provided by the respondent.
     *
     * <p>This field establishes a one-to-many relationship between {@code Response} and {@code Answer},
     * indicating that a single response can contain multiple answers. The {@code Map} uses the
     * question's unique identifier as the key, allowing for efficient retrieval and association
     * of answers to their respective questions.</p>
     *
     * <p>The {@code cascade = CascadeType.ALL} setting ensures that all persistence operations
     * (e.g., persist, merge, remove) applied to the response are cascaded to its answers. The
     * {@code orphanRemoval = true} setting ensures that any answer removed from the map is also
     * removed from the database, maintaining referential integrity.</p>
     *
     * @see CascadeType#ALL
     */
    @OneToMany(mappedBy = "response", cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKey(name = "questionId")
    private Map<Long, Answer> answers = new HashMap<>();

    /**
     * Retrieves the unique identifier of this response.
     *
     * @return the {@code id} of the response
     */
    public Long getId() {
        return id;
    }

    /**
     * Retrieves the survey associated with this response.
     *
     * @return the {@code survey} to which this response belongs
     */
    public Survey getSurvey() {
        return survey;
    }

    /**
     * Associates this response with a specific survey.
     *
     * <p>By setting the {@code survey}, you link this response to a particular survey,
     * enabling the organization of responses within their respective surveys.</p>
     *
     * @param survey the {@code Survey} to associate with this response
     */
    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    /**
     * Retrieves the map of answers associated with this response.
     *
     * @return a {@code Map} where the key is the question's {@code id} and the value is the corresponding {@link Answer}
     */
    public Map<Long, Answer> getAnswers() {
        return answers;
    }

    /**
     * Sets the map of answers for this response.
     *
     * <p>By setting the {@code answers}, you define the responses provided by the participant
     * for each question in the survey. This method replaces the current map of answers with
     * the provided map.</p>
     *
     * @param answers a {@code Map} of question {@code id}s to their corresponding {@code Answer} entities
     */
    public void setAnswers(Map<Long, Answer> answers) {
        this.answers = answers;
    }

    /**
     * Adds an answer to the response for a specific question.
     *
     * <p>This convenience method allows you to add an {@link Answer} to the {@code answers} map
     * by specifying the question's {@code id} and the corresponding {@code Answer} object.</p>
     *
     * @param questionId the {@code id} of the question to which the answer corresponds
     * @param answer     the {@code Answer} provided by the respondent for the specified question
     */
    public void addAnswer(Long questionId, Answer answer) {
        answers.put(questionId, answer);
        answer.setResponse(this);
    }

    /**
     * Removes an answer from the response for a specific question.
     *
     * <p>This convenience method allows you to remove an {@link Answer} from the {@code answers} map
     * by specifying the question's {@code id}. It also ensures that the association between the
     * answer and the response is properly severed.</p>
     *
     * @param questionId the {@code id} of the question whose answer should be removed
     */
    public void removeAnswer(Long questionId) {
        Answer removed = answers.remove(questionId);
        if (removed != null) {
            removed.setResponse(null);
        }
    }

    /**
     * Provides a string representation of the {@code Response} instance.
     *
     * @return a string containing the class name and its properties
     */
    @Override
    public String toString() {
        return "Response{" +
                "id=" + id +
                ", survey=" + (survey != null ? survey.getId() : null) +
                ", answers=" + answers.keySet() +
                '}';
    }

    /**
     * Compares this {@code Response} with another object for equality.
     *
     * <p>Two {@code Response} instances are considered equal if they have the same {@code id},
     * are associated with the same {@code Survey}, and contain identical mappings of question
     * identifiers to answers.</p>
     *
     * @param o the object to compare with this {@code Response}
     * @return {@code true} if the objects are equal; {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Response response = (Response) o;

        if (!id.equals(response.id)) return false;
        if (survey != null ? !survey.equals(response.survey) : response.survey != null) return false;
        return answers.equals(response.answers);
    }

    /**
     * Computes the hash code for this {@code Response}.
     *
     * @return an integer hash code value for this object
     */
    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (survey != null ? survey.hashCode() : 0);
        result = 31 * result + answers.hashCode();
        return result;
    }
}
