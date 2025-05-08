package com.bookstore.bookstore.service;

import com.bookstore.bookstore.common.util.JwtUtil;
import com.bookstore.bookstore.dto.AuthDto.AuthResponseDto;
import com.bookstore.bookstore.dto.AuthDto.LoginDto;
import com.bookstore.bookstore.entity.AccessToken;
import com.bookstore.bookstore.entity.RefreshToken;
import com.bookstore.bookstore.entity.User;
import com.bookstore.bookstore.exception.InvalidCredentialsException;
import com.bookstore.bookstore.repository.AuthRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@Service
@Transactional
public class AuthService {

    private final AuthRepository authRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Autowired
    public AuthService(
            AuthRepository authRepository,
            JwtUtil jwtUtil,
            PasswordEncoder passwordEncoder,
            TokenService tokenService
    ) {
        this.authRepository = authRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }


    /**
     * Authenticates a user based on provided login credentials and generates authentication tokens.
     * <p>
     * This method verifies the user's username and password. If valid, it generates a new access token
     * and a refresh token for the user. The generated tokens are stored in the database for later validation.
     * </p>
     *
     * @param loginDto The data transfer object containing user login information, such as username and password.
     * @return A {@link AuthResponseDto} containing the access token, refresh token, and user ID.
     * @throws InvalidCredentialsException if the provided username or password is incorrect.
     */
    public AuthResponseDto loginUser(LoginDto loginDto) {

        log.info("Logging in user with username: {}", loginDto.getUsername());

        User user = authRepository.findByUsername(loginDto.getUsername())
                .orElseThrow(InvalidCredentialsException::new);

        log.debug("User found with ID: {}", user.getId());

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        log.info("Login successful for user ID: {}", user.getId());
        log.debug("Generating access and refresh tokens for user ID: {}", user.getId());

        AccessToken accessTokenEntity = tokenService.createAccessTokenEntity(user);
        RefreshToken refreshTokenEntity = tokenService.createRefreshTokenEntity(accessTokenEntity);

        log.debug("Access token entity created with ID: {}", accessTokenEntity.getId());
        log.debug("Refresh token entity created with ID: {}", refreshTokenEntity.getId());

        String accessToken = jwtUtil.generateAccessToken(
                accessTokenEntity.getId(),
                user.getId(),
                user.getEmail(),
                user.getUserRole()
        );

        String refreshToken = jwtUtil.generateRefreshToken(
                refreshTokenEntity.getId(),
                user.getId()
        );

        log.debug("Access token generated: {}", accessToken);
        log.debug("Refresh token generated: {}", refreshToken);

        return AuthResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .id(user.getId())
                .build();
    }

}
