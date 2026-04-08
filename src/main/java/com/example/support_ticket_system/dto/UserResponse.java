package com.example.support_ticket_system.dto;

import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String email;
    private String role;
}
