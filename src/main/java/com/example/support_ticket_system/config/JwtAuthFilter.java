package com.example.support_ticket_system.config;

public class JwtAuthFilter {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    public String extractToken(String request) {
        if (request != null && request.startsWith(BEARER_PREFIX)) {
            return request.substring(BEARER_PREFIX.length());
        }
        return null;
    }

    public boolean validateToken(String token) {
        // Logic to be implemented
        return false;
    }

    public String getUsernameFromToken(String token) {
        // Logic to be implemented
        return null;
    }
    
}
