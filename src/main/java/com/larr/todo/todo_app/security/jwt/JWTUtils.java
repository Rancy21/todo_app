package com.larr.todo.todo_app.security.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Date;

import javax.crypto.SecretKey;

@Component
public class JWTUtils {
    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.expirationMs}")
    private int jwtExp;

    private static final Logger logger = LoggerFactory.getLogger(JWTUtils.class);

    private SecretKey key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String generateJwtToken(String userEmail) {
        return Jwts.builder().subject(userEmail).issuedAt(new Date())
                .expiration(new Date((new Date().getTime() + jwtExp))).signWith(key()).compact();
    }

    public String getJwtFromCookie(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, "jwt");
        if (cookie != null) {
            return cookie.getValue();
        }

        return null;
    }

    public ResponseCookie generateJwtCookie(String userEmail) {
        String jwt = generateJwtToken(userEmail);
        return ResponseCookie.from("jwt", jwt).path("/").maxAge(jwtExp / 1000).httpOnly(true).secure(true)
                .sameSite("Lax").build();
    }

    public ResponseCookie generateCleanJwtCookie() {
        return ResponseCookie.from("jwt", "").path("/").maxAge(0).httpOnly(true).secure(true).sameSite("Lax").build();
    }

    public String getEmailFromJwtToken(String token) {
        return Jwts.parser().verifyWith(key()).build().parseSignedClaims(token).getPayload().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().verifyWith(key()).build().parse(authToken);
            return true;
        } catch (Exception e) {
            logger.error("Validation Token Error: {}", e.getMessage());
        }

        return false;
    }

}
