package com.bookstore.bookstore.dto.AuthorDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorsDto {

    @Schema(description = "The id of the author", example = "1")
    private  Long id;

    @Schema(description = "The first name of the author", example = "Dante")
    private String firstName;

    @Schema(description = "The last name of the author", example = "Alighieri")
    private String lastName;

}
