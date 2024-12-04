package com.group23.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a survey created by the surveyor.
 *
 * <p>The {@code Survey} class encapsulates the details of a survey, including its title, description,
 * associated questions, and its availability status (open or closed for responses). This entity
 * is managed by JPA and maps to a corresponding table in the database, enabling CRUD operations
 * and relational mappings.</p>
 *
 * <p>Each {@code Survey} can contain multiple {@link Question} entities, establishing a one-to-many
 * relationship. The class provides convenience methods to manage the bi-directional association
 * between surveys and their questions, ensuring consistency and integrity of the relationship.</p>
 *
 *
 * @author Imad Mohamed
 * @version 1.0
 */
@Entity
public class Survey {

    /**
     * Unique identifier for the survey.
     *
     * <p>This field serves as the primary key for the {@code Survey} entity and is
     * automatically generated by the database upon insertion. It uniquely distinguishes
     * each survey within the survey system.</p>
     *
     * @see GenerationType#IDENTITY
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Title of the survey.
     *
     * <p>This field holds the name or title of the survey, providing a clear and concise
     * description of the survey's purpose. It is typically displayed to respondents to
     * inform them about the survey's topic.</p>
     *
     * <p><strong>Example:</strong> "Customer Satisfaction Survey"</p>
     */
    private String title;

    /**
     * Description of the survey.
     *
     * <p>This field provides a detailed explanation of the survey, outlining its objectives,
     * scope, and any additional information that might help respondents understand the context
     * and purpose of the survey.</p>
     *
     * <p><strong>Example:</strong> "A survey to gauge customer satisfaction levels and gather feedback on our services."</p>
     */
    private String description;

    /**
     * List of questions in the survey.
     *
     * <p>This field establishes a one-to-many relationship between {@code Survey} and {@code Question},
     * indicating that a single survey can contain multiple questions. The {@code cascade = CascadeType.ALL}
     * setting ensures that all persistence operations (e.g., persist, merge, remove) applied to the
     * survey are cascaded to its questions. The {@code orphanRemoval = true} setting ensures that any
     * question removed from the list is also removed from the database, maintaining referential integrity.</p>
     *
     * <p>The {@code mappedBy = "survey"} attribute indicates that the {@code Survey} entity is not the
     * owner of the relationship, and the {@code Question} entity maintains the foreign key.</p>
     */
    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("survey-question")
    private List<Question> questions = new ArrayList<>();

    // One-to-Many relationship with Response
    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("survey-response")
    private List<Response> responses = new ArrayList<>();


    /**
     * Indicates whether the survey is open for responses.
     *
     * <p>This boolean field determines the availability of the survey to respondents.
     * When {@code true}, the survey accepts responses; when {@code false}, the survey
     * is closed and no longer accepts new responses.</p>
     *
     * <p><strong>Usage Scenario:</strong> After a certain period, the surveyor may decide to
     * close the survey to prevent further submissions and proceed with analyzing the collected data.</p>
     */
    private boolean isOpen;

    // Constructors

    /**
     * Default constructor for {@code Survey}.
     *
     * <p>Initializes a new instance of the {@code Survey} class. This constructor is required
     * by JPA for entity instantiation.</p>
     */
    public Survey() {
        // Default constructor required by JPA
    }

    /**
     * Parameterized constructor for {@code Survey}.
     *
     * <p>Allows the creation of a {@code Survey} instance with specified title, description,
     * and open status.</p>
     *
     * @param title       the title of the survey
     * @param description the description of the survey
     * @param isOpen      the open status of the survey
     */
    public Survey(String title, String description, boolean isOpen) {
        this.title = title;
        this.description = description;
        this.isOpen = isOpen;
    }

    // Getters and Setters

    /**
     * Retrieves the unique identifier of this survey.
     *
     * @return the {@code id} of the survey
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier for this survey.
     *
     * <p><strong>Note:</strong> Be cautious when setting IDs manually, as they are typically managed by JPA.
     * Manual setting of IDs may lead to conflicts or unintended behavior in the persistence context.</p>
     *
     * @param id the {@code id} to set for the survey
     */
    public void setId(Long id) {
        this.id = id; // Be cautious when setting IDs manually; usually managed by JPA
    }

    /**
     * Retrieves the title of this survey.
     *
     * @return the {@code title} of the survey
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of this survey.
     *
     * <p>By setting the {@code title}, you define the primary name or heading of the survey,
     * which is typically displayed to respondents and used to identify the survey within the system.</p>
     *
     * <p><strong>Example:</strong> "Employee Engagement Survey"</p>
     *
     * @param title the {@code title} to set for the survey
     */
    public void setTitle(String title) {
        this.title = title; // Validation can be added here if needed
    }

    /**
     * Retrieves the description of this survey.
     *
     * @return the {@code description} of the survey
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of this survey.
     *
     * <p>By setting the {@code description}, you provide detailed information about the survey's
     * purpose, objectives, and any other relevant context that helps respondents understand
     * the intent and importance of the survey.</p>
     *
     * <p><strong>Example:</strong> "A survey to assess employee satisfaction and gather feedback on workplace improvements."</p>
     *
     * @param description the {@code description} to set for the survey
     */
    public void setDescription(String description) {
        this.description = description; // Validation can be added here if needed
    }

    /**
     * Retrieves the list of questions associated with this survey.
     *
     * @return a {@code List} of {@link Question} entities included in the survey
     */
    public List<Question> getQuestions() {
        return questions;
    }

    /**
     * Sets the list of questions for this survey.
     *
     * <p>By setting the {@code questions}, you define the set of questions that will be presented
     * to respondents as part of the survey. This method replaces the current list of questions with
     * the provided list.</p>
     *
     * @param questions a {@code List} of {@link Question} entities to associate with this survey
     */
    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    /**
     * Retrieves the open status of this survey.
     *
     * @return {@code true} if the survey is open for responses; {@code false} otherwise
     */
    public boolean getIsOpen() {
        return isOpen;
    }

    /**
     * Sets the open status of this survey.
     *
     * <p>By setting the {@code isOpen} flag, you control whether the survey is available for respondents
     * to submit their answers. This is useful for managing the survey lifecycle, such as closing the survey
     * after a certain period or once a target number of responses has been reached.</p>
     *
     * @param isOpen {@code true} to open the survey for responses; {@code false} to close it
     */
    public void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    // Optional: Convenience methods to manage bi-directional relationships

    /**
     * Adds a question to this survey.
     *
     * <p>This convenience method allows you to add a {@link Question} to the {@code questions} list
     * while simultaneously setting the association on the {@code Question} side. This ensures that
     * the bi-directional relationship between {@code Survey} and {@code Question} remains consistent.</p>
     *
     * <p><strong>Example:</strong></p>
     * <pre>{@code
     * Question question = new MultipleChoiceQuestion();
     * question.setText("How satisfied are you with our service?");
     * survey.addQuestion(question);
     * }</pre>
     *
     * @param question the {@code Question} to add to the survey
     */
    public void addQuestion(Question question) {
        questions.add(question);
        question.setSurvey(this);
    }

    /**
     * Removes a question from this survey.
     *
     * <p>This convenience method allows you to remove a {@link Question} from the {@code questions} list
     * while simultaneously nullifying the association on the {@code Question} side. This ensures that
     * the bi-directional relationship between {@code Survey} and {@code Question} remains consistent.</p>
     *
     * <p><strong>Example:</strong></p>
     * <pre>{@code
     * Question question = survey.getQuestions().get(0);
     * survey.removeQuestion(question);
     * }</pre>
     *
     * @param question the {@code Question} to remove from the survey
     */
    public void removeQuestion(Question question) {
        questions.remove(question);
        question.setSurvey(null);
    }

    /**
     * Provides a string representation of the {@code Survey} instance.
     *
     * @return a string containing the class name and its properties
     */
    @Override
    public String toString() {
        return "Survey{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", isOpen=" + isOpen +
                ", questionsCount=" + questions.size() +
                '}';
    }

    /**
     * Compares this {@code Survey} with another object for equality.
     *
     * <p>Two {@code Survey} instances are considered equal if they have the same {@code id},
     * {@code title}, {@code description}, and {@code isOpen} status.</p>
     *
     * @param o the object to compare with this {@code Survey}
     * @return {@code true} if the objects are equal; {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;                                  // If the same object, return true
        if (o == null || getClass() != o.getClass()) return false;

        Survey survey = (Survey) o;

        return isOpen == survey.isOpen &&
                Objects.equals(id, survey.id) &&
                Objects.equals(title, survey.title) &&
                Objects.equals(description, survey.description);
    }

    /**
     * Computes the hash code for this {@code Survey}.
     *
     * @return an integer hash code value for this object
     */
    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + (isOpen ? 1 : 0);
        return result;
    }
}
