package com.klu.jwtvalidation;

import org.springframework.web.bind.annotation.*;
import java.util.Date;

import io.jsonwebtoken.Jwts;

@RestController
@RequestMapping("/jwt")
public class JwtController {

    JwtUtil jwtUtil = new JwtUtil();

    // Generate token for testing
    @GetMapping("/generate")
    public String generateToken() {

        return Jwts.builder()
            .subject("Rakshith")
            .issuedAt(new Date())
            .expiration(
                new Date(System.currentTimeMillis() + 3600000)
            )
            .signWith(
                io.jsonwebtoken.security.Keys.hmacShaKeyFor(
                    "mysecretkeymysecretkeymysecretkey123".getBytes()
                )
            )
            .compact();
    }

    // Validate token
    @PostMapping("/validate")
    public String validate(
        @RequestHeader("Authorization") String token) {

        token = token.replace("Bearer ", "");

        if (jwtUtil.validateToken(token)) {
            return "Token is Valid";
        }

        return "Invalid or Expired Token";
    }

    // Extract claims
    @PostMapping("/claims")
    public String claims(
        @RequestHeader("Authorization") String token) {

        token = token.replace("Bearer ", "");

        if (jwtUtil.validateToken(token)) {
            return "Username: "
                + jwtUtil.extractUsername(token);
        }

        return "Invalid Token";
    }
}