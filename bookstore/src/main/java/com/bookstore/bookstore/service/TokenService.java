package com.bookstore.bookstore.service;

import com.bookstore.bookstore.common.util.JwtUtil;
import com.bookstore.bookstore.entity.AccessToken;
import com.bookstore.bookstore.entity.RefreshToken;
import com.bookstore.bookstore.entity.User;
import com.bookstore.bookstore.repository.AccessTokenRepository;
import com.bookstore.bookstore.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Service responsible for managing and creating access and refresh tokens for user authentication.
 * <p>
 * This service interacts with the database to save tokens associated with a user and handles
 * token expiration details.
 * </p>
 */

@Service
@Transactional
public class TokenService {

    private final AccessTokenRepository accessTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;

    @Autowired
    public TokenService (
            AccessTokenRepository accessTokenRepository,
            RefreshTokenRepository refreshTokenRepository,
            JwtUtil jwtUtil
    ) {
        this.accessTokenRepository = accessTokenRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Creates and saves an access token entity for a given user.
     * <p>
     * The method initializes an {@link AccessToken} entity with the user, calculates the expiration
     * time based on the access token duration defined in {@link JwtUtil}, and stores the creation
     * and update timestamps. The token is then saved to the database.
     * </p>
     *
     * @param user The user for whom the access token is created.
     * @return The created and saved {@link AccessToken} entity.
     */
    public<T> AccessToken createAccessTokenEntity(User user) {

        AccessToken accessTokenEntity = new AccessToken();

        accessTokenEntity.setUser(user);
        accessTokenEntity.setExpiresAt(LocalDateTime.now().plus(jwtUtil.getAccessTokenExpiration(), ChronoUnit.MILLIS));
        accessTokenEntity.setCreatedAt(LocalDateTime.now());
        accessTokenEntity.setUpdatedAt(LocalDateTime.now());

        accessTokenEntity = accessTokenRepository.save(accessTokenEntity);
        return accessTokenEntity;

    }

    /**
     * Creates and saves a refresh token entity for a given access token.
     * <p>
     * This method initializes a {@link RefreshToken} entity, links it to an existing access token,
     * and calculates the expiration time based on the refresh token duration defined in {@link JwtUtil}.
     * It also sets the creation and update timestamps, and then saves the entity to the database.
     * </p>
     *
     * @param accessTokenEntity The access token entity associated with the new refresh token.
     * @return The created and saved {@link RefreshToken} entity.
     */
    public RefreshToken createRefreshTokenEntity(AccessToken accessTokenEntity) {

        RefreshToken refreshTokenEntity = new RefreshToken();
        refreshTokenEntity.setAccessToken(accessTokenEntity);
        refreshTokenEntity.setExpiresAt(LocalDateTime.now().plus(jwtUtil.getRefreshTokenExpiration(), ChronoUnit.MILLIS));
        refreshTokenEntity.setCreatedAt(LocalDateTime.now());
        refreshTokenEntity.setUpdatedAt(LocalDateTime.now());

        refreshTokenEntity = refreshTokenRepository.save(refreshTokenEntity);
        return refreshTokenEntity;

    }

}

