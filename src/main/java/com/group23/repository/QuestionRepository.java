package com.group23.repository;

import com.group23.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for {@link Question} entities.
 */
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    // Additional query methods if needed
}
