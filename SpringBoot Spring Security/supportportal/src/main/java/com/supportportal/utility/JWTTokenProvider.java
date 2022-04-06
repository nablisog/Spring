package com.supportportal.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import static com.supportportal.constant.SecurityConstant.*;
import static java.util.Arrays.stream;


import com.auth0.jwt.exceptions.JWTVerificationException;
import com.supportportal.domain.UserPrinciple;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;


import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JWTTokenProvider {

    @Value("${jwt.secret}")
    private String secrect;

    public String generateJwtToken(UserPrinciple userPrinciple){
        String[] claims = getClaimsFromUser(userPrinciple);
        return JWT.create().
                withIssuer(GET_ARRAYS_LLC).
                withAudience(GET_ARRAYS_ADMIN)
                .withIssuedAt(new Date())
                .withSubject(userPrinciple.getUsername())
                .withArrayClaim(AUTHORITIES, claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(secrect.getBytes()));

    }

    public List<GrantedAuthority> getAuthorities(String token){
        String[] claims = getClaimFromToken(token);
        return stream(claims).map(SimpleGrantedAuthority :: new).collect(Collectors.toList());
    }

    public Authentication getAuthentication(String username, List<GrantedAuthority> authorities, HttpServletRequest request){
        UsernamePasswordAuthenticationToken userPasswordAuthToken = new UsernamePasswordAuthenticationToken(username,null,authorities);
        userPasswordAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return userPasswordAuthToken;

    }

    public boolean isTokenValid(String username,String token){
        JWTVerifier verifier = getJwtverifier();
        return StringUtils.isNotEmpty(username) && !isTokenExpired(verifier, token);

    }

    private boolean isTokenExpired(JWTVerifier verifier, String token) {
        Date expiration = verifier.verify(token).getExpiresAt();
        return expiration.before(new Date());
    }

    public String getSubject(String token){
        JWTVerifier verifier = getJwtverifier();
        return verifier.verify(token).getSubject();
    }


    private String[] getClaimFromToken(String token) {
        JWTVerifier verifier = getJwtverifier();
        return verifier.verify(token).getClaim(AUTHORITIES).asArray(String.class);
    }


    private JWTVerifier getJwtverifier() {
        JWTVerifier verifier = null;
        try{
            Algorithm algorithm = Algorithm.HMAC512(secrect);
            verifier = JWT.require(algorithm).withIssuer(GET_ARRAYS_LLC).build();
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException(TOKEN_CANNOT_BE_VERIFIED);
        }
        return verifier;
    }

    private String[] getClaimsFromUser(UserPrinciple user) {
        List<String> authorites = new ArrayList<>();
        for (GrantedAuthority grantedAuthority : user.getAuthorities()){
            authorites.add(grantedAuthority.getAuthority());

        }
        return authorites.toArray(new String[0]);
    }



}
