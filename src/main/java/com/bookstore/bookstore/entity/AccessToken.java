package com.bookstore.bookstore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents an access token for user authentication. This entity stores information about
 * the token's status, expiration, and timestamps for creation and updates.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "access_token")
public class AccessToken {

    @Id
    @Column(columnDefinition = "CHAR(36)")
    private String id;

    @ManyToOne()
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id"
    )
    private User user;

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
     * Checks if the access token has expired.
     *
     * @return true if the current date and time are after the expiration time (expiresAt), indicating
     *         that the token is no longer valid; false otherwise.
     */
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }

}


