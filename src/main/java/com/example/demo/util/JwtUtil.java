package com.example.demo.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    private final long expirationMs = 1000 * 60 * 5; // 5 min and after "403 Error"
    private final long refreshExpirationMs = 1000L * 60 * 60 * 24 * 7; // 7 days

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username) // Вместо setSubject
                .issuedAt(new Date()) // Вместо setIssuedAt
                .expiration(new Date(System.currentTimeMillis() + expirationMs)) // Вместо setExpiration
                .signWith(getKey())
                .compact();
    }

    public String generateRefreshToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + refreshExpirationMs))
                .signWith(getKey())
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser() // Вместо parserBuilder()
                .verifyWith(getKey()) // Вместо setSigningKey()
                .build()
                .parseSignedClaims(token) // Вместо parseClaimsJws()
                .getPayload() // Вместо getBody()
                .getSubject();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser() // Вместо parserBuilder()
                    .verifyWith(getKey()) // Вместо setSigningKey()
                    .build()
                    .parseSignedClaims(token); // Вместо parseClaimsJws()
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
}
