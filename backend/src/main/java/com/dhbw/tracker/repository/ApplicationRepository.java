package com.dhbw.tracker.repository;

import com.dhbw.tracker.model.Application;
import com.dhbw.tracker.model.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Data access layer for Application entities.
 *
 * Extends JpaRepository to inherit all standard CRUD operations:
 * save(), findById(), findAll(), deleteById(), etc.
 *
 * The derived query method findByStatus() is automatically
 * implemented by Spring Data JPA at runtime - no SQL needed.
 */
@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    /**
     * Filters all application records matching the given status.
     * Spring Data JPA generates: SELECT * FROM applications WHERE status = ?
     *
     * @param status the ApplicationStatus enum value to filter by
     * @return a list of matching Application entities
     */
    List<Application> findByStatus(ApplicationStatus status);
}
