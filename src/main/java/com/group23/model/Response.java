package com.group23.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a user's response to a survey.
 */
@Entity
public class Response {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The survey to which this response belongs.
     */
    @ManyToOne
    @JoinColumn(name = "survey_id")
    @JsonBackReference("survey-response")
    private Survey survey;

    /**
     * List of answers provided by the respondent.
     */
    @OneToMany(mappedBy = "response", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("response-answer")
    private List<Answer> answers = new ArrayList<>();

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    // Optional: Convenience methods

    public void addAnswer(Answer answer) {
        answers.add(answer);
        answer.setResponse(this);
    }

    public void removeAnswer(Answer answer) {
        answers.remove(answer);
        answer.setResponse(null);
    }

    // equals and hashCode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Response response = (Response) o;

        return Objects.equals(id, response.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    //toString() used for debugging
    @Override
    public String toString() {
        return "Response{" +
                "id=" + id +
                ", survey=" + (survey != null ? survey.getId() : "null") +
                ", answersCount=" + (answers != null ? answers.size() : 0) +
                ", answers=" + answers +
                '}';
    }

}
