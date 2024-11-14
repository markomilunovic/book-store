package com.bookstore.bookstore.service;

import com.bookstore.bookstore.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

/**
 * Service for loading user-specific data for authentication and authorization purposes.
 * <p>
 * This class implements {@link UserDetailsService}, providing methods to retrieve user details
 * based on either username or user ID. It is used by Spring Security during authentication to load
 * user data from the database.
 * </p>
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthRepository authRepository;

    @Autowired
    public CustomUserDetailsService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    /**
     * Loads a user by username for authentication.
     * <p>
     * This method retrieves user details based on the username provided. It is used by Spring Security
     * during the authentication process to validate user credentials.
     * </p>
     *
     * @param username The username of the user.
     * @return A {@link UserDetails} object containing user information.
     * @throws UsernameNotFoundException if a user with the specified username does not exist.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return authRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    /**
     * Loads a user by user ID.
     * <p>
     * This method retrieves user details based on the user ID provided. It is used for authorization purposes
     * in cases where user details are required for security context setup.
     * </p>
     *
     * @param id The ID of the user.
     * @return A {@link UserDetails} object containing user information.
     * @throws UsernameNotFoundException if a user with the specified ID does not exist.
     */
    public UserDetails loadUserById(Long id) {
        return authRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
    }

}

