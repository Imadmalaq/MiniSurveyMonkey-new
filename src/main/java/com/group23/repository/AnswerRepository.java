package com.group23.repository;

import com.group23.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for {@link Answer} entities.
 */
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    // Additional query methods can be defined here if needed.
}
