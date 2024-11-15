package com.bookstore.bookstore.common.util;

import com.bookstore.bookstore.common.ErrorResponse;
import com.bookstore.bookstore.exception.BookAlreadyExistsException;
import com.bookstore.bookstore.exception.BookNotFoundException;
import com.bookstore.bookstore.exception.InvalidCredentialsException;
import com.bookstore.bookstore.exception.InvalidFileException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * Global exception handler for managing application-wide exceptions.
 * <p>
 * This class provides centralized handling of exceptions by defining specific
 * methods for common error cases, such as unhandled exceptions, HTTP status-related
 * exceptions, and specific business-related exceptions.
 * </p>
 *
 * <p>
 * Each handler method constructs a standardized {@link ErrorResponse} and returns an
 * appropriate HTTP status code, promoting consistency across API responses.
 * </p>
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles any unhandled exceptions that occur within the application.
     * <p>
     * This catch-all handler returns an error response with an HTTP 500
     * status code when an unhandled exception is thrown. The response includes
     * an error message and the details of the original request.
     * </p>
     *
     * @param ex      The exception that was not specifically handled.
     * @param request The current web request during which the exception occurred.
     * @return A {@link ResponseEntity} containing an {@link ErrorResponse} with an
     *         error message and a status of {@link HttpStatus#INTERNAL_SERVER_ERROR}.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex, WebRequest request) {
        log.error("Unhandled exception occurred: {}", ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    /**
     * Handles exceptions when a book with the specified ISBN already exists.
     * <p>
     * This method captures {@link BookAlreadyExistsException} instances, which
     * indicate that an attempt was made to create a book with an ISBN that
     * already exists in the system. It provides an error response with
     * a specific message and HTTP status code, reflecting the exception details.
     * </p>
     *
     * @param ex      The {@link BookAlreadyExistsException} containing information
     *                about the duplicate book ISBN.
     * @param request The current web request during which the exception occurred.
     * @return A {@link ResponseEntity} containing an {@link ErrorResponse} with the
     *         error message and the status associated with the exception.
     */
    @ExceptionHandler(BookAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleBookAlreadyExistsException(BookAlreadyExistsException ex, WebRequest request) {
        log.error("BookAlreadyExistsException: {}", ex.getReason(), ex);

        ErrorResponse errorResponse = new ErrorResponse(
                ex.getReason(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }


    /**
     * Handles exceptions when a requested book is not found.
     * <p>
     * This method captures {@link BookNotFoundException} instances, which indicate
     * that an attempt was made to retrieve or modify a book that does not exist in the system.
     * It constructs an error response with a specific message and HTTP 404 status code.
     * </p>
     *
     * @param ex      The {@link BookNotFoundException} containing information about the missing book.
     * @param request The current web request during which the exception occurred.
     * @return A {@link ResponseEntity} containing an {@link ErrorResponse} with the
     *         error message and a status of {@link HttpStatus#NOT_FOUND}.
     */
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBookNotFoundException(BookNotFoundException ex, WebRequest request) {
        log.error("BookNotFoundException: {}", ex.getReason(), ex);

        ErrorResponse errorResponse = new ErrorResponse(
                ex.getReason(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles exceptions for invalid login credentials.
     * <p>
     * This method captures {@link InvalidCredentialsException} instances, which are
     * thrown when a user provides incorrect username or password during login. It returns
     * an error response with a specific message and an HTTP 401 Unauthorized status.
     * </p>
     *
     * @param ex      The {@link InvalidCredentialsException} indicating invalid login credentials.
     * @param request The current web request during which the exception occurred.
     * @return A {@link ResponseEntity} containing an {@link ErrorResponse} with a
     *         specific error message and a status of {@link HttpStatus#UNAUTHORIZED}.
     */
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handeInvalidCredentialsException(InvalidCredentialsException ex, WebRequest request) {
        log.warn("InvalidCredentialsException: {}", ex.getReason());

        ErrorResponse errorResponse = new ErrorResponse(
                ex.getReason(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, ex.getStatusCode());
    }

    /**
     * Handles exceptions of type {@link InvalidFileException} thrown within the application.
     * <p>
     * This exception handler catches instances of `InvalidFileException` that occur when
     * an invalid file is uploaded for import. It logs the error message and constructs a standardized
     * {@link ErrorResponse} to be returned to the client with a `400 Bad Request` status.
     * </p>
     *
     * @param ex      the {@link InvalidFileException} that was thrown
     * @param request the {@link WebRequest} during which the exception occurred, used for contextual information
     * @return a {@link ResponseEntity} containing the {@link ErrorResponse} and a `400 Bad Request` HTTP status
     */
    @ExceptionHandler(InvalidFileException.class)
    public ResponseEntity<ErrorResponse> handleInvalidFileException(InvalidFileException ex, WebRequest request) {
        log.error("InvalidFileException: {}", ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


}
