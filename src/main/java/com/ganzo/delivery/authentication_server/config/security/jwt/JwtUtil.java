package com.ganzo.delivery.authentication_server.config.security.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {
    @Value("${token.timeout:1}")
    private Integer TOKEN_DURATION;

    @Value("${token.secret.key}")
    private String key;

    public JwtUtil() { }

    public Date durationToken() {
        return new Date(System.currentTimeMillis() + (1000*60*60*TOKEN_DURATION));
    }

    public String generateToken(UserDetails userDetails, Map<String, Object> claims) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(durationToken())
                .signWith(SignatureAlgorithm.HS512, key)
                .addClaims(claims).compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        return userDetails.getUsername().equals(this.extractUsername(token)) && !isTokenExpired(token);
    }

    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    public Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
    }

    public String getToken() {
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getHeader("Authorization");
        return token.split(" ")[1];
    }

    public Long getClaimIdUserByNameField() {
        return getClaims(getToken()).get("id", Long.class);
    }
}
