package com.dhbw.tracker.exception;

import java.time.LocalDateTime;

/**
 * Data Transfer Object representing a structured JSON error envelope.
 *
 * Returned by the GlobalExceptionHandler for all handled exceptions,
 * ensuring the API never exposes raw Java stack traces to the client
 * and always responds with a consistent, machine-readable error format.
 *
 * Example JSON output:
 * {
 *   "status": 404,
 *   "error": "Not Found",
 *   "message": "Application not found with id: 99",
 *   "timestamp": "2024-06-04T14:30:00",
 *   "path": "/api/applications/99"
 * }
 */
public class ErrorResponse {

    private int status;
    private String error;
    private String message;
    private LocalDateTime timestamp;
    private String path;

    public ErrorResponse(int status, String error, String message,
                         LocalDateTime timestamp, String path) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.timestamp = timestamp;
        this.path = path;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getPath() {
        return path;
    }
}
