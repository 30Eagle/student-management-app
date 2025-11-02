package com.example.demo.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtService {
    private static final long EXPIRATION = 1000 * 60 * 60; // 1 hour

    @Value("${JWT_SECRET:myDefaultSecretKeyThatIsAtLeast256BitsLongForHS256Algorithm}")
    private String jwtSecret;

    private SecretKey getSigningKey() {
        // Use environment variable if set, otherwise use default secret
        byte[] keyBytes = Base64.getDecoder().decode(
            Base64.getEncoder().encodeToString(jwtSecret.getBytes())
        );
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(secretKey)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
