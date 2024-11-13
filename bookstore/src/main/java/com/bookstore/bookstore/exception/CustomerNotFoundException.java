package com.bookstore.bookstore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Exception thrown when attempting to fetch a customer that does not exist in the system.
 * <p>
 * This exception returns a 404 Not Found HTTP status, indicating that a customer with the specified
 * ID is not present in the database. It is typically used in customer retrieval operations.
 * </p>
 */
public class CustomerNotFoundException extends ResponseStatusException {

    public CustomerNotFoundException(Long customerId) {
        super(HttpStatus.NOT_FOUND, "Customer with ID" + customerId + " not found.");
    }

}
