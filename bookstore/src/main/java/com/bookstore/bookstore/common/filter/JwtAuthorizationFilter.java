package com.bookstore.bookstore.common.filter;

import com.bookstore.bookstore.common.token.RoleBasedAuthenticationToken;
import com.bookstore.bookstore.common.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import javax.crypto.SecretKey;

/**
 * Filter that handles role-based authorization for incoming HTTP requests.
 * <p>
 * This filter intercepts each request, validates the JWT token, and sets the user’s role
 * in the {@link SecurityContextHolder} if they are authorized. Unauthorized requests are rejected.
 * </p>
 */
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final SecretKey accessSecretKey;

    @Autowired
    public JwtAuthorizationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        this.accessSecretKey = jwtUtil.getAccessTokenSecret();
    }

    /**
     * Processes each HTTP request, validates the JWT token present, and checks the user's role.
     * <p>
     * This method checks the Authorization header for a JWT token. If a token is present and valid,
     * it retrieves the user's role and sets the authorization context for the current request.
     * </p>
     *
     * @param request     The HTTP request to be filtered.
     * @param response    The HTTP response associated with the request.
     * @param filterChain Provides access to the next filter in the chain for further processing.
     * @throws ServletException if an error occurs during filtering.
     * @throws IOException      if an I/O error occurs during filtering.
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String jwt = JwtUtil.getJwtFromRequest(request);

        if (StringUtils.hasText(jwt)) {

            jwtUtil.validateToken(jwt, accessSecretKey);

            String role = jwtUtil.getClaimFromToken(jwt, accessSecretKey, "role");

            if ("ADMIN".equals(role) || "FINANCE".equals(role)) {
                SecurityContextHolder.getContext().setAuthentication(new RoleBasedAuthenticationToken(role));
            } else {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

}
