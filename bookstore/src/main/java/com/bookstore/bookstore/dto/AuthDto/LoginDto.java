package com.bookstore.bookstore.dto.AuthDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for handling user login requests.
 * This DTO encapsulates the necessary information required for a user to log in,
 * specifically the user's username and password.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

    @Schema(description = "Username of the user", example = "user123")
    @NotEmpty(message = "Username field must not be empty")
    private String username;

    @Schema(description = "Password for the user account", example = "password123")
    @NotEmpty(message = "Password field must not be empty")
    private String password;

}

