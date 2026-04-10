package com.example.support_ticket_system.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TicketResponse {

    private Long id;
    private String title;
    private String description;
    private String status;
    private String priority;
    private UserResponse assignedTo;
    private UserResponse createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}