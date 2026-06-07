package com.dhbw.tracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * JPA Entity representing a single dual-study job application.
 * Maps directly to the "applications" table in the SQLite database.
 * Input is protected by Bean Validation annotations enforced at
 * the controller boundary before any persistence call occurs.
 */
@Entity
@Table(name = "applications")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Company name must not be blank")
    @Column(name = "company_name", nullable = false)
    private String companyName;

    @NotBlank(message = "Position title must not be blank")
    @Column(name = "position_title", nullable = false)
    private String positionTitle;

    @NotNull(message = "Status is required (APPLIED, INTERVIEWING, REJECTED, OFFERED)")
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ApplicationStatus status;

    @Column(name = "application_date", nullable = false)
    private LocalDate applicationDate;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    /**
     * Automatically sets applicationDate to today if not provided
     * by the client. Runs before every INSERT operation.
     */
    @PrePersist
    protected void onPrePersist() {
        if (this.applicationDate == null) {
            this.applicationDate = LocalDate.now();
        }
    }

    // --- Getters & Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPositionTitle() {
        return positionTitle;
    }

    public void setPositionTitle(String positionTitle) {
        this.positionTitle = positionTitle;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public LocalDate getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(LocalDate applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
