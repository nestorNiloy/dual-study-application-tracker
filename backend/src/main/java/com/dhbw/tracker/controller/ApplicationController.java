package com.dhbw.tracker.controller;

import com.dhbw.tracker.model.Application;
import com.dhbw.tracker.model.ApplicationStatus;
import com.dhbw.tracker.service.ApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller exposing the Application Tracker API endpoints.
 *
 * This layer is responsible only for HTTP concerns: mapping routes,
 * deserializing request bodies, triggering Bean Validation via @Valid,
 * and returning the correct HTTP status codes.
 *
 * All business logic is fully delegated to the ApplicationService layer.
 * The controller has no direct knowledge of the repository or database.
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    /**
     * POST /api/applications
     * Creates and persists a new application entry.
     * @Valid triggers Bean Validation before the method body executes.
     *
     * @param application the deserialized JSON request body
     * @return 201 Created with the saved application (including its generated id)
     */
    @PostMapping
    public ResponseEntity<Application> createApplication(
            @Valid @RequestBody Application application) {

        Application savedApplication = applicationService.createApplication(application);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedApplication);
    }

    /**
     * GET /api/applications
     * Retrieves every application record from the database.
     *
     * @return 200 OK with a JSON array of all application objects
     */
    @GetMapping
    public ResponseEntity<List<Application>> getAllApplications() {
        List<Application> applications = applicationService.getAllApplications();
        return ResponseEntity.ok(applications);
    }

    /**
     * GET /api/applications/status/{status}
     * Filters applications by their current status.
     * Example: GET /api/applications/status/OFFERED
     *
     * @param status the status string from the URI path (case-insensitive)
     * @return 200 OK with a JSON array of matching application objects
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Application>> getApplicationsByStatus(
            @PathVariable String status) {

        ApplicationStatus applicationStatus = ApplicationStatus.valueOf(status.toUpperCase());
        List<Application> applications = applicationService.getApplicationsByStatus(applicationStatus);
        return ResponseEntity.ok(applications);
    }

    /**
     * PUT /api/applications/{id}
     * Fully updates an existing application record.
     * Returns 404 if the id does not exist (handled by GlobalExceptionHandler).
     *
     * @param id          the primary key of the record to update
     * @param application the deserialized JSON request body with new values
     * @return 200 OK with the fully updated application object
     */
    @PutMapping("/{id}")
    public ResponseEntity<Application> updateApplication(
            @PathVariable Long id,
            @Valid @RequestBody Application application) {

        Application updatedApplication = applicationService.updateApplication(id, application);
        return ResponseEntity.ok(updatedApplication);
    }

    /**
     * DELETE /api/applications/{id}
     * Permanently removes an application record from the database.
     * Returns 404 if the id does not exist (handled by GlobalExceptionHandler).
     *
     * @param id the primary key of the record to delete
     * @return 204 No Content (success with no response body, per REST convention)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        applicationService.deleteApplication(id);
        return ResponseEntity.noContent().build();
    }
}
