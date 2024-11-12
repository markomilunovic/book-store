package com.bookstore.bookstore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Exception thrown when attempting to create a book with an ISBN that already exists in the system.
 * <p>
 * This exception returns a 409 Conflict HTTP status, indicating that a book with the specified
 * ISBN is already present in the database. It is typically used in book creation operations to
 * prevent duplicate entries.
 * </p>
 */

public class BookAlreadyExistsException extends ResponseStatusException{

    public BookAlreadyExistsException(String isbn) {
        super(HttpStatus.CONFLICT, "Book with isbn " + isbn + "already exists.");
    }

}
