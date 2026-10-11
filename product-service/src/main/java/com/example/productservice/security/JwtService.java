package com.example.productservice.security;


import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;

import java.util.Date;
import java.util.function.Function;
import javax.crypto.SecretKey;
@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secretKey;    private SecretKey getSigningKey() {
        byte[] keyBytes = secretKey.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public String generateToken(String email){
        return Jwts.builder()
                .setSubject(email)
            .setExpiration(new Date (System.currentTimeMillis()+60*60*1000))
                .signWith(getSigningKey())
                .compact();
    }
    public String extractEmail(String token){
    return extractClaim(token,Claims::getSubject);
    }
    public boolean isValid(String token){
        Claims claims=extractAllClaims(token);
        Date date= claims.getExpiration();
        return date.after(new Date());
    }
    private Claims extractAllClaims(String token){
    return Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
    Claims claims=extractAllClaims(token);
    return claimsResolver.apply(claims);
    }
}
