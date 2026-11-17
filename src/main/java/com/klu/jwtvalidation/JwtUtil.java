package com.klu.jwtvalidation;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

public class JwtUtil {

    private final String secret =
        "mysecretkeymysecretkeymysecretkey123";

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(
            secret.getBytes(StandardCharsets.UTF_8)
        );
    }

    // Validate token
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token);

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    // Extract claims
    public Claims extractClaims(String token) {
        return Jwts.parser()
            .verifyWith(getKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    // Extract username
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }
}