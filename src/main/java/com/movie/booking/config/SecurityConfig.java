package com.movie.booking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final DatabaseRoleJwtAuthenticationConverter
            databaseRoleJwtAuthenticationConverter;

    public SecurityConfig(
            DatabaseRoleJwtAuthenticationConverter converter) {
        this.databaseRoleJwtAuthenticationConverter = converter;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
            throws Exception {

        httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        
                		.requestMatchers(
                                "/api/users/register",
                                "/api/auth/login")
                        .permitAll()

                        .requestMatchers(
                                HttpMethod.POST, "/api/theatre")
                        .hasRole("THEATRE_ADMIN")

                        .requestMatchers(
                                HttpMethod.DELETE, "/api/theatre/**")
                        .hasRole("ADMIN")
                        
                        .requestMatchers(HttpMethod.PUT,
                                "/api/theatre/*/approve",
                                "/api/theatre/*/reject")
                        .hasRole("ADMIN")
                        
                        .requestMatchers(HttpMethod.POST, "/api/screen")
                        .hasAnyRole("ADMIN", "THEATRE_ADMIN")
                        
                        .requestMatchers(HttpMethod.DELETE, "/api/screen/*")
                        .hasAnyRole("ADMIN", "THEATRE_ADMIN")
                        
                        .requestMatchers(HttpMethod.POST,"/api/seattype")
                        .hasRole("ADMIN")

                        .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(
                                databaseRoleJwtAuthenticationConverter)));

        return httpSecurity.build();
    }
}