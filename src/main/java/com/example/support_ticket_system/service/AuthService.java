package com.example.support_ticket_system.service;

import com.example.support_ticket_system.dto.RegisterRequest;
import com.example.support_ticket_system.dto.AuthResponse;
import com.example.support_ticket_system.dto.LoginRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}