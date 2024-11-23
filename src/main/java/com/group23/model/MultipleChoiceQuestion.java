package com.group23.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a multiple-choice question within a survey.
 */
@Entity
public class MultipleChoiceQuestion extends Question {

    /**
     * The list of options available for this multiple-choice question.
     * Establishes a bidirectional one-to-many relationship with {@code Option}.
     */
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Option> options = new ArrayList<>();

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;

        // Maintain the bidirectional relationship
        for (Option option : options) {
            option.setQuestion(this);
        }
    }
}
