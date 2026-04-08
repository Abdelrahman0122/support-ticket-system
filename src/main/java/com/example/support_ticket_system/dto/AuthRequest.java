package com.example.support_ticket_system.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}