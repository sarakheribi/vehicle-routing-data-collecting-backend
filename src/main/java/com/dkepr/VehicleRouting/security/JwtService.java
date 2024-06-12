package com.dkepr.VehicleRouting.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    private SecretKey generateSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    public String generateJwtToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + (1000 * 24 * 24)))
                .signWith(generateSigningKey())
                .compact();
    }

    public Claims extractClaims(String jwtToken) {
        return Jwts
                .parser()
                .verifyWith(generateSigningKey())
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload();
    }

    public String extractSubject(String jwtToken) {
        return extractClaims(jwtToken).getSubject();
    }

    public Date extractExpiration(String jwtToken) {
        return extractClaims(jwtToken).getExpiration();
    }

    public boolean isTokenValid(String jwtToken) {
        return new Date().before(extractExpiration(jwtToken));
    }
}