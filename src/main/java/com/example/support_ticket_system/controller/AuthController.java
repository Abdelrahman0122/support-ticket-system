package com.example.support_ticket_system.controller;

import com.example.support_ticket_system.dto.AuthResponse;
import com.example.support_ticket_system.dto.LoginRequest;
import com.example.support_ticket_system.dto.RegisterRequest;
import com.example.support_ticket_system.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}