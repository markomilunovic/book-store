package com.bookstore.bookstore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Exception thrown when the provided login credentials are invalid.
 * <p>
 * This exception triggers an HTTP 401 Unauthorized response, indicating
 * that the username or password provided is incorrect.
 * </p>
 */
public class InvalidCredentialsException extends ResponseStatusException {

    /**
     * Constructs a new {@code InvalidCredentialsException} with a default message.
     */
    public InvalidCredentialsException() {
        super(HttpStatus.UNAUTHORIZED, "Invalid username or password.");
    }
}

