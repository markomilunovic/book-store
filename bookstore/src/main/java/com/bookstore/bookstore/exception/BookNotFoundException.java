package com.bookstore.bookstore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BookNotFoundException extends ResponseStatusException {

    public BookNotFoundException(Long id) {
        super(HttpStatus.CONFLICT, "Book with id " + id + " not found.");
    }

}
