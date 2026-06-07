package com.dhbw.tracker.model;

/**
 * Represents the lifecycle state of a dual-study application.
 * Used as a typed Enum column in the database to ensure only
 * valid states can be persisted.
 */
public enum ApplicationStatus {
    APPLIED,
    INTERVIEWING,
    REJECTED,
    OFFERED
}
