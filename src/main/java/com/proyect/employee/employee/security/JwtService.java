package com.proyect.employee.employee.security;

import com.proyect.employee.employee.config.EnvironmentConfig;
import com.proyect.employee.employee.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Autowired
    EnvironmentConfig environmentConfig;

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateToken(CustomUserDetails user){
        return createToken(new HashMap<>(), user);
    }
    private String createToken(Map<String, Object> extraClaims, CustomUserDetails user){
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(user.getId().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + environmentConfig.getExpiration_key()))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // * IF TOKEN BELONGS FROM USER
    // ? IF TOKEN IS EXPIRED
    public boolean isTokenValid(String token, CustomUserDetails user){
        String userId = getUserIdFromToken(token);
        return userId.equals(user.getId().toString()) && !isTokenExpired(token);
    }


    public String getUserIdFromToken(String token){
        return extractClaim(token, Claims::getSubject);
    }

    private Date getExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date(System.currentTimeMillis()));
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    private Key getKey(){
        byte[] keyBytes = Decoders.BASE64.decode(environmentConfig.getSecret_key());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
