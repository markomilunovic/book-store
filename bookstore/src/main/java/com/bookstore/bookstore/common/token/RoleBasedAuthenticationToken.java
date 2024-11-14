package com.bookstore.bookstore.common.token;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import java.util.Collections;

public class RoleBasedAuthenticationToken extends UsernamePasswordAuthenticationToken {

    public RoleBasedAuthenticationToken(String role) {
        super(null, null, Collections.singleton(() -> "ROLE_" + role.toUpperCase()));
    }
}
