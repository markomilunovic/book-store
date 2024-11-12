package com.bookstore.bookstore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

/**
 * Data Transfer Object representing a book in the system.
 * <p>
 * This DTO is used to transfer book data, including unique identification,
 * bibliographic details, and availability information. It is typically returned
 * as a response object from API endpoints when accessing or manipulating book records.
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDto {

    @Schema(description = "Unique identifier of the book", example = "1")
    private Long id;

    @Schema(description = "The name of the book", example = "The Divine Comedy")
    private String name;

    @Schema(description = "The original name of the book, useful for translations", example = "Divina Commedia")
    private String originalName;

    @Schema(description = "Total number of pages in the book", example = "928")
    private int pageCount;

    @Schema(description = "International Standard Book Number (ISBN) of the book", example = "978-3-16-148410-0")
    private String isbn;

    @Schema(description = "The publication date of the book", example = "1320-01-28")
    private LocalDate publicationDate;

    @Schema(description = "Number of available copies in the store", example = "10")
    private int availableCopies;

    @Schema(description = "The genre of the book", example = "Poem")
    private String genre;

}
