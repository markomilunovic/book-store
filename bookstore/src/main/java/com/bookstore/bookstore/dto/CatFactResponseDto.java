package com.bookstore.bookstore.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for holding a cat fact retrieved from an external API.
 * <p>
 * This DTO contains a single field, {@code catFact}, which stores the cat fact
 * to be returned to the user.
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CatFactResponseDto {

    @JsonProperty("fact")
    @Schema(description = "The cat fact to return to the user")
    private  String catFact;

}
