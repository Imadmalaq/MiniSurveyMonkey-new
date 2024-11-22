package com.group23.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents an answer to a specific question within a response.
 */
@Entity
public class Answer {


    public Answer(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The response to which this answer belongs.
     */
    @ManyToOne
    @JoinColumn(name = "response_id", nullable = false)
    private Response response;

    /**
     * The question to which this answer corresponds.
     */
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    /**
     * Textual answer for open-ended questions.
     */
    private String text;

    /**
     * Numeric answer for numeric range questions.
     */
    private Integer number;

    /**
     * Selected option ID for multiple-choice questions.
     */
    private Long selectedOptionId;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Long getSelectedOptionId() {
        return selectedOptionId;
    }

    public void setSelectedOptionId(Long selectedOptionId) {
        this.selectedOptionId = selectedOptionId;
    }

    // equals and hashCode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Answer answer = (Answer) o;

        return Objects.equals(id, answer.id);
    }
    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", question=" + (question != null ? question.getId() : "null") +
                ", text='" + text + '\'' +
                ", number=" + number +
                ", selectedOptionId=" + selectedOptionId +
                '}';
    }



    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
