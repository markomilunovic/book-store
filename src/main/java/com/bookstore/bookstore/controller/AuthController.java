package com.bookstore.bookstore.controller;

import com.bookstore.bookstore.dto.AuthDto.AuthResponseDto;
import com.bookstore.bookstore.dto.AuthDto.LoginDto;
import com.bookstore.bookstore.dto.ResponseDto;
import com.bookstore.bookstore.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Controller responsible for handling authentication-related endpoints.
 * <p>
 * This controller uses {@link AuthService} to perform the actual authentication logic.
 * It logs authentication attempts and responses to help with monitoring and debugging.
 * </p>
 */
@Slf4j
@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(
            AuthService authService
    ) {
        this.authService = authService;
    }


    @Operation(
            summary = "Authenticate user and return tokens",
            description = "Logs in the user and returns access and refresh tokens along with user ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login successful",
                    content = @Content(schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "Invalid login data",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<ResponseDto<AuthResponseDto>> login(@Valid @RequestBody LoginDto loginDto) {

        log.info("User login attempt with username: {}", loginDto.getUsername());
        log.debug("Login request data: {}", loginDto);

        AuthResponseDto authResponseDto = authService.loginUser(loginDto);

        log.info("Login successful for user: {}", loginDto.getUsername());

        ResponseDto<AuthResponseDto> response = new ResponseDto<>(authResponseDto, "Login successful");
        return ResponseEntity.ok(response);
    }

}
