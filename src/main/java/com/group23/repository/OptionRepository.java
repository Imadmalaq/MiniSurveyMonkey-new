package com.group23.repository;

import com.group23.model.Option;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for {@link Option} entities.
 */
public interface OptionRepository extends JpaRepository<Option, Long> {
    // Additional query methods can be defined here if needed.
}
