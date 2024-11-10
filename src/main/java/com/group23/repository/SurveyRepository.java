package com.group23.repository;

import com.group23.model.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for {@link Survey} entities.
 * Provides basic CRUD operations.
 * Extends JpaRepository to provide CRUD operations on Survey entities.
 * Custom query methods can be added here to enhance survey retrieval functionality.
 */
public interface SurveyRepository extends JpaRepository<Survey, Long> {
    // Additional query methods can be defined here if needed.
}
