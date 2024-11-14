package com.bookstore.bookstore.dto.AuthDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for representing the response of an authenticated user.
 * This DTO is used to encapsulate the essential information sent to the client
 * after a successful login or authentication, including the user's ID, access token,
 * and refresh token.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDto {

    @Schema(description = "The access token issued to the authenticated user for authorization.")
    @NotEmpty(message = "Access Token field must not be empty")
    private String accessToken;

    @Schema(description = "The refresh token issued to the authenticated user for token renewal.")
    @NotEmpty(message = "Refresh Token field must not be empty")
    private String refreshToken;

    @Schema(description = "The unique ID of the authenticated user.", example = "123")
    @NotNull(message = "User ID field must not be empty")
    private Long id;

}

