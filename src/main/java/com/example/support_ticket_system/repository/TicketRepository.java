package com.example.support_ticket_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.support_ticket_system.domain.entity.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    
}
