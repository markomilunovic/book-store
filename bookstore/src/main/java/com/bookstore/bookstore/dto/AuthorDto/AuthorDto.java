package com.bookstore.bookstore.dto.AuthorDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object representing an author.
 * <p>
 * This DTO is used to transfer author data, including the author's first and last name.
 * It is typically used as part of a book’s details in API requests and responses.
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto {

    @Schema(description = "The first name of the author", example = "Dante")
    @NotBlank(message = "Author's first name cannot be blank.")
    private String firstName;

    @Schema(description = "The last name of the author", example = "Alighieri")
    @NotBlank(message = "Author's last name cannot be blank.")
    private String lastName;

}

