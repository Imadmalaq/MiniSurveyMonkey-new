package com.group23.model;


import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a survey created by the surveyor.
 */
@Entity
public class Survey {

    /**
     * Unique identifier for the survey.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Title of the survey.
     */
    private String title;

    /**
     * Description of the survey.
     */
    private String description;

    /**
     * List of questions in the survey.
     */
    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions = new ArrayList<>();

    /**
     * Indicates whether the survey is open for responses.
     */
    private boolean isOpen;

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id; // Be cautious when setting IDs manually; usually managed by JPA
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title; // Validation can be added here if needed
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description; // Validation can be added here if needed
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public boolean getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    // Optional: Convenience methods to manage bi-directional relationships

    public void addQuestion(Question question) {
        questions.add(question);
        question.setSurvey(this);
    }

    public void removeQuestion(Question question) {
        questions.remove(question);
        question.setSurvey(null);
    }
}
