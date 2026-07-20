package com.ecommerce.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;


import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Component
public class JwtUtil {

    // Nota: Por ahora la dejamos acá para probar, pero en el futuro
    // la moveremos al archivo application.properties.
    private final String SECRET_KEY = "MiClaveSecretaSuperSeguraParaEcommerceBackend";

    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 2;

    private Key getSigningKey(){
     return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generarToken(String email, String rol){
        Map<String, Object> claims = new HashMap<>();
        claims.put("rol", rol);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extraerEmail(String token){
        return extraerClaims(token).getSubject();
    }

    public boolean esTokenValido(String token){
        try {
            return !extraerClaims(token).getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    private Claims extraerClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
