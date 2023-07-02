package com.practice.demo.util;

import com.practice.demo.entity.Account;
import io.jsonwebtoken.Claims;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class JwtUtil {
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    private static final String SECRET_KEY = "apC56MPCEENmNOTaY3liBeljGvmnLM2SZPVccp+WJW53xIe8l8qJLpgfqHgmBmYb5qtk8A1vYOnSDCY9S9BpVA==";
    private static final long EXPIRATION_TIME = 86400000;

    public static String generateToken(Account user) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION_TIME);
        SecretKey secretKey = getKey();
        Map<String,Object> claimMap = new HashMap<>();
        claimMap.put("name", user.getName());
        claimMap.put("email", user.getEmail());
        claimMap.put("roles", "ROLE_USER");

        return Jwts.builder()
                .setSubject(user.getName())
                .setIssuer("demoApi")
                .addClaims(claimMap)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(secretKey,SignatureAlgorithm.HS512)
                .compact();
    }


    public static Claims getTokenBody(String token){
        Claims claims = null;
        try{
            claims =Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build().parseClaimsJws(token).getBody();
        }catch (Exception e){
            e.printStackTrace();
        }

        return claims;
    }

    public static boolean validateToken(String token) {
            try{
                Jwts.parserBuilder()
                        .setSigningKey(getKey())
                        .build()
                        .parseClaimsJws(token.replace("Bearer ", ""))
                        .getBody()
                        .getSubject();
            }catch (Exception e){
                return false;
            }
            return true;

    }

    public static Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    public static boolean isExpiration(String token){
        try{
            return getTokenBody(token).getExpiration().before(new Date());
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }
    private static SecretKey getKey(){
        return Keys.hmacShaKeyFor(JwtUtil.SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }


    public static Authentication getAuthentication(String token) {
        Claims claims = parseToken(token);

        String username = claims.getSubject();
        List<GrantedAuthority> authorities = Arrays.stream(claims.get("roles").toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return username != null ?
                new UsernamePasswordAuthenticationToken(username, null, authorities) :
                null;
    }
}
