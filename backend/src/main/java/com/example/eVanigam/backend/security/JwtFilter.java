package com.example.eVanigam.backend.security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)

            throws ServletException, IOException {

        // Read Authorization header
        String authHeader =
                request.getHeader("Authorization");

        // Check whether token exists
        if(authHeader == null ||
                !authHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);
            return;
        }

        // Remove "Bearer "
        String token =
                authHeader.substring(7);

        // Validate token
        if(jwtUtil.validateToken(token)) {

            // Extract email
        String email = jwtUtil.extractEmail(token);
        String role = jwtUtil.extractRole(token);
                
        List<SimpleGrantedAuthority> authorities =List.of(new SimpleGrantedAuthority(role));

            // Create authenticated user
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            email,
                            null,
                            authorities
                    );

            authToken.setDetails(
                    new WebAuthenticationDetailsSource()
                            .buildDetails(request)
            );

            // Tell Spring user is authenticated
            SecurityContextHolder.getContext()
                    .setAuthentication(authToken);
        }

        filterChain.doFilter(request, response);
    }
}