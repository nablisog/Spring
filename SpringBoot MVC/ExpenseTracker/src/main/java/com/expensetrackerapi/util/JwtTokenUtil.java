package com.expensetrackerapi.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String secret;
    private final long jwt_token_validity = 5 * 60 * 60;

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder().setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwt_token_validity * 1000))
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }
    public String getUserNameFromToken(String JwtToken){
        return getClaimsFromToken(JwtToken,Claims::getSubject);
    }

    public Date getExprationDateFromToken(String JwtToken){
        return getClaimsFromToken(JwtToken,Claims::getExpiration);
    }

    private <T> T getClaimsFromToken(String token, Function<Claims, T> ClaimsResolver){
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return ClaimsResolver.apply(claims);
    }


    public boolean validateToken(String JwtToken, UserDetails userDetails) {
        String username =  getUserNameFromToken(JwtToken);
       return username.equals(userDetails.getUsername()) && !isTokenExpired(JwtToken);
    }

    private boolean isTokenExpired(String jwtToken) {
        Date expiration = getExprationDateFromToken(jwtToken);
        return expiration.before(new Date());
    }
}
