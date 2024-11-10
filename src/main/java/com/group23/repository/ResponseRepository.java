package com.group23.repository;

import com.group23.model.Response;
import com.group23.model.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for {@link Response} entities.
 * Extends JpaRepository to provide CRUD operations on Response entities.
 * Contains a custom method to find all responses associated with a specific survey.
 */
@Repository
public interface ResponseRepository extends JpaRepository<Response, Long> {
    List<Response> findBySurvey(Survey survey);
}
