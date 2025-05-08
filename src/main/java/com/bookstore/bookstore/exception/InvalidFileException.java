package com.bookstore.bookstore.exception;

/**
 * Exception thrown when an invalid file is provided for import.
 * <p>
 * This exception is used to indicate issues with the uploaded file format,
 * such as when the file is empty, missing, or has an unsupported format.
 * </p>
 */
public class InvalidFileException extends RuntimeException {
    public InvalidFileException(String message) {
        super(message);
    }
}
