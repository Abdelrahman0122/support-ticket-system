package com.example.support_ticket_system.security;

import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtService {
   private final String SECERET = "seceretkey";

    public String generateToken(String email){
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, SECERET)
                .compact();
    }

    public String extrackEmail(String token) {
        return Jwts.parser()
                .setSigningKey(SECERET)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
