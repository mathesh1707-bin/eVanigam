package com.example.eVanigam.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.eVanigam.backend.security.JwtFilter;

@Configuration
public class SecurityConfig {

    private JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
    // Public routes first
    .requestMatchers("/users/register", "/users/login").permitAll()

    // Admin-only product mutations
    .requestMatchers(HttpMethod.POST, "/products/**").hasRole("ADMIN")
    .requestMatchers(HttpMethod.PUT, "/products/**").hasRole("ADMIN")
    .requestMatchers(HttpMethod.DELETE, "/products/**").hasRole("ADMIN")

    // Public product browsing — AFTER admin rules
    .requestMatchers(HttpMethod.GET, "/products/**").permitAll()

    // Cart needs auth — explicit before anyRequest
    .requestMatchers("/cart/**").authenticated()

    .anyRequest().authenticated()
)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    

}