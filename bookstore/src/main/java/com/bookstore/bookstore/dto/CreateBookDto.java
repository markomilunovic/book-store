package com.bookstore.bookstore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * Data Transfer Object for creating a new book record.
 * <p>
 * This DTO contains the information required to create a new book in the system. Validation
 * constraints are applied to ensure that necessary information is provided and meets certain
 * criteria.
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBookDto {

    @Schema(description = "The name of the book", example = "The Divine Comedy")
    @NotBlank(message = "Book name cannot be blank. Please provide a valid name.")
    private String name;

    @Schema(description = "The original name of the book", example = "Divina Commedia")
    @NotBlank(message = "Book original name cannot be blank. Please provide a valid original name.")
    private String originalName;

    @Schema(description = "Total number of pages in the book", example = "928")
    @NotNull(message = "Number of pages is required.")
    @Min(value = 1, message = "Number of pages must be at least 1.")
    private int pageCount;

    @Schema(description = "International Standard Book Number (ISBN) of the book", example = "978-3-16-148410-0")
    @NotNull(message = "ISBN is required.")
    @Pattern(
            regexp = "^[\\d-]{10,17}$",
            message = "ISBN must be a numeric string between 10 and 17 characters, with optional hyphens."
    )
    private String isbn;

    @Schema(description = "The publication date of the book", example = "1320-01-28")
    @PastOrPresent(message = "Publication date cannot be in the future.")
    private LocalDate publicationDate;

    @Schema(description = "Number of available copies in the store", example = "10")
    @NotNull(message = "Number of available copies is required.")
    @Min(value = 0, message = "Number of available copies cannot be negative.")
    private int availableCopies;

    @Schema(description = "The genre of the book", example = "Poem")
    private String genre;

    @Schema(description = "List of authors associated with the book, each represented by an AuthorDto")
    @NotNull(message = "Authors are required.")
    @Size(min = 1, message = "At least one author is required.")
    private List<AuthorDto> authors;

}
