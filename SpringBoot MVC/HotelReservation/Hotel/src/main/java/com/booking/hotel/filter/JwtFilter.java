package com.booking.hotel.filter;

import com.booking.hotel.service.CustomerUserDetail;
import com.booking.hotel.utility.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomerUserDetail userDetail;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = null;
        String userName  = null;
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")){
            token = header.substring(7);
            userName = jwtUtil.extractUsername(token);
        }
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null){
            var detail = userDetail.loadUserByUsername(userName);
            if(jwtUtil.validateToken(token,detail)){
                var authenticationToken = new UsernamePasswordAuthenticationToken(
                        detail,null,detail.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource()
                        .buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request,response);
    }

}
