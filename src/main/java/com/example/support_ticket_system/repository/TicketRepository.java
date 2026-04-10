package com.example.support_ticket_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.support_ticket_system.domain.entity.Ticket;
import com.example.support_ticket_system.domain.entity.enums.TicketStatus;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByCreatedById(Long userId);

    List<Ticket> findByAssignedToId(Long userId);

    List<Ticket> findByStatus(TicketStatus status);

    @Query("SELECT t FROM Ticket t WHERE t.createdBy.id = :userId OR t.assignedTo.id = :userId")
    List<Ticket> findByCreatorOrAssignee(@Param("userId") Long userId);
}
