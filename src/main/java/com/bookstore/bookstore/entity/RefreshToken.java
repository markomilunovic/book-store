package com.bookstore.bookstore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents a refresh token associated with an access token, used for generating
 * new access tokens. Contains information about the token's status, expiration, and timestamps.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "refresh_token")
public class RefreshToken {

    @Id
    @Column(columnDefinition = "CHAR(36)")
    private String id;

    @OneToOne()
    @JoinColumn(
            name = "access_token_id",
            referencedColumnName = "id",
            nullable = false
    )
    private AccessToken accessToken;

    @Column(
            name = "is_revoked",
            nullable = false
    )
    private Boolean isRevoked = false;

    @Column(
            name = "expires_at",
            nullable = false
    )
    private LocalDateTime expiresAt;

    @Column(
            name = "created_at",
            nullable = false
    )
    private LocalDateTime createdAt;

    @Column(
            name = "updated_at",
            nullable = false
    )
    private LocalDateTime updatedAt = LocalDateTime.now();

    /**
     * Initializes the entity with a randomly generated UUID if not set.
     */
    @PrePersist
    public void initializeIdIfNotSet() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }

    /**
     * Checks if the refresh token has expired.
     *
     * @return true if the current date and time are after the expiration time (expiresAt), indicating
     *         that the token is no longer valid; false otherwise.
     */
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }

}


