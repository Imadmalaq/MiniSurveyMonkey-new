package com.group23.model;

import jakarta.persistence.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a user's response to a survey.
 */
@Entity
public class Response {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Survey survey;

    @OneToMany(mappedBy = "response", cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKey(name = "questionId")
    private Map<Long, Answer> answers = new HashMap<>();

    // Getters and setters

    public Long getId() {
        return id;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    public Map<Long, Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<Long, Answer> answers) {
        this.answers = answers;
    }
}
