package com.expensetrackerapi.security;

import com.expensetrackerapi.util.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

       String tokenHeader =  request.getHeader("AUTHORIZATION");
        String jwtToken = null;
        String username= null;
       if (tokenHeader != null && tokenHeader.startsWith("Bearer ")){
           jwtToken = tokenHeader.substring(7);
           try {
               username = jwtTokenUtil.getUserNameFromToken(jwtToken);

           } catch (IllegalArgumentException e) {
               throw new RuntimeException("Unable to get JWT token");
           }
           catch (ExpiredJwtException e) {
               throw new RuntimeException("t JWT token has expired");
           }
       }
       if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
           UserDetails userDetails =  customUserDetailService.loadUserByUsername(username);
           if (jwtTokenUtil.validateToken(jwtToken,userDetails)){
               UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                       userDetails,null, userDetails.getAuthorities());
               authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
               SecurityContextHolder.getContext().setAuthentication(authToken);
           }
       }
       filterChain.doFilter(request,response);
    }
}
