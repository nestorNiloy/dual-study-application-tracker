package com.dhbw.tracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown by the Service layer when a requested Application record
 * cannot be found in the database by its primary key.
 *
 * The @ResponseStatus annotation maps this exception to HTTP 404
 * when it propagates unhandled, but it is also caught explicitly
 * by the GlobalExceptionHandler for a clean JSON error envelope.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
