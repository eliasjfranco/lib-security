package com.mock.security.service;

import com.mock.security.config.JwtAuthProperties;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class JwtService {

    private final SecretKey secretKey;
    private final JwtAuthProperties props;

    public JwtService(JwtAuthProperties props) {
        this.secretKey = Keys.hmacShaKeyFor(props.getSecret().getBytes());
        this.props = props;
    }

    public String generateToken(String username, Integer tenantId, Map<String, Object> extraClaims, String role) {
        Map<String, Object> claims = new HashMap<>(Optional.ofNullable(extraClaims).orElse(new HashMap<>()));
        claims.put("roles", props.getRolePrefix() + role);
        claims.put("tenantId", tenantId);
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .subject(username)
                .claim("tenantId", tenantId)
                .claims(claims)
                .issuedAt(new Date(now))
                .expiration(new Date(now + props.getExpirationMillis()))
                .signWith(secretKey)
                .compact();
    }

    public String extractUsername(String token) throws JwtException {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public Integer extractTenantId(String token) throws JwtException {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("tenantId", Integer.class);
    }

    public String extractRole(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("roles", String.class);
    }
}
