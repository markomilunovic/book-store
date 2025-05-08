package com.bookstore.bookstore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Exception thrown when attempting to fetch a book that does not exist in the system.
 * <p>
 * This exception returns a 404 Not Found HTTP status, indicating that a book with the specified
 * ID is not present in the database. It is typically used in book retrieval operations.
 * </p>
 */
public class BookNotFoundException extends ResponseStatusException {

    public BookNotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND, "Book with id " + id + " not found.");
    }

}
