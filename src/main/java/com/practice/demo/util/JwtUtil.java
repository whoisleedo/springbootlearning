package com.practice.demo.util;

import com.practice.demo.entity.Account;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.data.repository.query.QueryLookupStrategy;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    private static final String SECRET_KEY = "apC56MPCEENmNOTaY3liBeljGvmnLM2SZPVccp+WJW53xIe8l8qJLpgfqHgmBmYb5qtk8A1vYOnSDCY9S9BpVA==";
    private static final long EXPIRATION_TIME = 86400000;

    public static String generateToken(Account user) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION_TIME);
        SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        Map<String,Object> claimMap = new HashMap<>();
        claimMap.put("name", user.getName());
        claimMap.put("email", user.getEmail());

        return Jwts.builder()
                .setSubject(user.getName())
                .setIssuer("demoApi")
                .addClaims(claimMap)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(secretKey,SignatureAlgorithm.HS512)
                .compact();
    }





}
