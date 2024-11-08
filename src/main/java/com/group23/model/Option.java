package com.group23.model;

import jakarta.persistence.*;

/**
 * Represents an option for a multiple-choice question.
 */
@Entity
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    // Getters and setters

    public Long getId() {
        return id;
    }

    // No setter for 'id' since it's auto-generated

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
