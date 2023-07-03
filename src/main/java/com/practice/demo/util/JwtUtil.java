package com.practice.demo.util;


import com.practice.demo.entity.Account;
import io.jsonwebtoken.*;
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
    private static final long EXPIRATION_TIME = 300000;


    public static String generateToken(Account user) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION_TIME);
        SecretKey secretKey = getKey();
        Map<String,Object> detailMap = new HashMap<>();
        detailMap.put("preferred_username", user.getUserAccount());
        detailMap.put("email", user.getEmail());
        detailMap.put("roles", "ROLE_USER");

        return Jwts.builder()
                .addClaims(detailMap)
                .setSubject(user.getName())
                .setIssuer("demoApi")
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();

    }

    public static boolean validateToken(String token) {
            try{
                Jwts.parserBuilder()
                        .setSigningKey(getKey())
                        .build()
                        .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
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


    private static SecretKey getKey(){
        return Keys.hmacShaKeyFor(JwtUtil.SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }


    public static Authentication getAuthentication(String token) {
        Claims claims = parseToken(token);
        String username = (String)claims.get("preferred_username");

        List<GrantedAuthority> authorities = Arrays.stream(claims.get("roles").toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return username != null ?
                new UsernamePasswordAuthenticationToken(username, null, authorities) :
                null;
    }
}
