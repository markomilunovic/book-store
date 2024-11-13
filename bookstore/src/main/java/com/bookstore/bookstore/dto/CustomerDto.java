package com.bookstore.bookstore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

    @Schema(description = "Unique identifier of the customer", example = "1")
    private Long id;

    @Schema(description = "The first name of the customer", example = "John")
    private String firstName;

    @Schema(description = "The last name of the customer", example = "Doe")
    private String lastName;

    @Schema(description = "The address of the customer", example = "Via Argiro, 52, Bari")
    private String address;

    @Schema(description = "The email of the customer", example = "customer@example.com")
    private String email;

}
