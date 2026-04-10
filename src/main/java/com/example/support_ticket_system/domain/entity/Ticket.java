package com.example.support_ticket_system.domain.entity;

import java.time.LocalDateTime;
import com.example.support_ticket_system.domain.entity.enums.TicketStatus;
import com.example.support_ticket_system.domain.entity.enums.Priority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(255)")
    private TicketStatus status;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(255)")
    private Priority priority;

    @ManyToOne
    private User assignedTo;

    @ManyToOne
    private User createdBy;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
