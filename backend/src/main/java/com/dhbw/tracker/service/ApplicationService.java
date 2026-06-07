package com.dhbw.tracker.service;

import com.dhbw.tracker.model.Application;
import com.dhbw.tracker.model.ApplicationStatus;

import java.util.List;

/**
 * Service interface defining the business logic contract for
 * the Application Tracker domain.
 *
 * Programming to an interface (not the implementation) ensures
 * loose coupling between the Controller and Service layers,
 * and makes the codebase straightforward to unit test.
 */
public interface ApplicationService {

    /**
     * Persists a new application entry to the database.
     *
     * @param application the validated Application object from the controller
     * @return the saved Application with its generated id
     */
    Application createApplication(Application application);

    /**
     * Retrieves every application record from the database.
     *
     * @return a list of all Application entities
     */
    List<Application> getAllApplications();

    /**
     * Filters and retrieves applications matching a specific status.
     *
     * @param status the ApplicationStatus enum value to filter by
     * @return a list of Application entities with the matching status
     */
    List<Application> getApplicationsByStatus(ApplicationStatus status);

    /**
     * Applies a full update to an existing application record.
     * Throws ResourceNotFoundException if no record with the given id exists.
     *
     * @param id                 the primary key of the record to update
     * @param applicationDetails the new field values to apply
     * @return the updated and re-persisted Application entity
     */
    Application updateApplication(Long id, Application applicationDetails);

    /**
     * Permanently removes an application record from the database.
     * Throws ResourceNotFoundException if no record with the given id exists.
     *
     * @param id the primary key of the record to delete
     */
    void deleteApplication(Long id);
}
