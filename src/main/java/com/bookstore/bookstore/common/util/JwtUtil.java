package com.bookstore.bookstore.common.util;

import com.bookstore.bookstore.common.enums.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey accessSecretKey;
    private final SecretKey refreshSecretKey;
    private final Long accessTokenExpiration;
    private final Long refreshTokenExpiration;

    public JwtUtil(
            @Value("${jwt.access-token-secret}")
            String accessTokenSecret,

            @Value("${jwt.refresh-token-secret}")
            String refreshTokenSecret,

            @Value("${jwt.accessTokenExpiration}")
            Long accessTokenExpiration,

            @Value("${jwt.refreshTokenExpiration}")
            Long refreshTokenExpiration
    ) {
        this.accessSecretKey = Keys.hmacShaKeyFor(accessTokenSecret.getBytes());
        this.refreshSecretKey = Keys.hmacShaKeyFor(refreshTokenSecret.getBytes());
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    public String generateAccessToken(String accessTokenId, Long userId, String username, UserRole userRole) {
        return Jwts.builder()
                .id(accessTokenId)
                .subject(String.valueOf(userId))
                .claim("username", username)
                .claim("role", userRole)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .signWith(accessSecretKey)
                .compact();
    }

    public String generateRefreshToken(String refreshTokenId, Long userId) {
        return Jwts.builder()
                .id(refreshTokenId)
                .subject(String.valueOf(userId))
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
                .signWith(refreshSecretKey)
                .compact();
    }

    public void validateToken(String token, SecretKey secretKey) throws JwtException {
        Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token);
    }

    public Long getUserIdFromToken(String token, SecretKey secretKey) throws JwtException {
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return Long.parseLong(claims.getSubject());
    }

    public String getClaimFromToken(String token, SecretKey secretKey, String claimName) {
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.get(claimName, String.class);
    }

    public String getTokenId(String token, SecretKey secretKey) throws JwtException {
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getId();
    }

    /**
     * Retrieves the JWT token from the Authorization header of the HTTP request.
     * <p>
     * This method checks if the Authorization header contains a bearer token, and if so,
     * extracts and returns the JWT by removing the "Bearer " prefix.
     * </p>
     *
     * @param request The HTTP request containing the Authorization header.
     * @return The JWT token as a {@link String}, or {@code null} if no valid bearer token is present.
     */
    public static String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public SecretKey getAccessTokenSecret() {
        return accessSecretKey;
    }

    public SecretKey getRefreshTokenSecret() {
        return refreshSecretKey;
    }

    public long getAccessTokenExpiration() {
        return accessTokenExpiration;
    }

    public long getRefreshTokenExpiration() {
        return refreshTokenExpiration;
    }

}

