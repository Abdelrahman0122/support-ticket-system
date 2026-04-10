package com.example.support_ticket_system.dto;

import lombok.Data;

@Data
public class TicketRequest {

    private String title;
    private String description;
    private String priority;
    private Long assignedToId;
}