package com.example.ticketPlatform.util;

import org.springframework.security.oauth2.jwt.Jwt;

import java.util.UUID;

public final class JwtUtil {
    void jwtUtil(){
    }
    public static UUID parseUserId(Jwt jwt) {
        return UUID.fromString(jwt.getSubject());
    }

}
