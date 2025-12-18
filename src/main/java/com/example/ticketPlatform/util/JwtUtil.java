package com.example.ticketPlatform.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class JwtUtil {
    void jwtUtil() {
    }

    public static UUID parseUserId(Jwt jwt) {
        return UUID.fromString(jwt.getSubject());
    }

    public static class JwtRoleExtractor {

        @SuppressWarnings("unchecked")
        public static String extractRoleEndingWithRole(String jwtToken) {
            DecodedJWT jwt = JWT.decode(jwtToken);

            // Get realm_access claim
            Claim realmAccessClaim = jwt.getClaim("realm_access");
            if (realmAccessClaim.isNull()) {
                throw new IllegalStateException("JWT has no realm_access claim");
            }

            Map<String, Object> realmAccess = realmAccessClaim.asMap();
            if (!realmAccess.containsKey("roles")) {
                throw new IllegalStateException("No roles found in realm_access");
            }

            List<String> roles = (List<String>) realmAccess.get("roles");

            // Filter roles ending with "_ROLE"
            List<String> filteredRoles = roles.stream()
                    .filter(role -> role.endsWith("_ROLE"))
                    .toList();

            if (filteredRoles.isEmpty()) {
                throw new IllegalStateException("No role ending with _ROLE found");
            }

            if (filteredRoles.size() > 1) {
                throw new IllegalStateException("Multiple roles ending with _ROLE found: " + filteredRoles);
            }

            return filteredRoles.get(0);
        }
}
}
