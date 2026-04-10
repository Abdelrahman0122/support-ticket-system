package com.example.support_ticket_system.service;

import com.example.support_ticket_system.dto.TicketRequest;
import com.example.support_ticket_system.dto.TicketResponse;

import java.util.List;

public interface TicketService {
    List<TicketResponse> getAllTickets();
    List<TicketResponse> getMyTickets();
    List<TicketResponse> getAssignedTickets();
    TicketResponse getTicketById(Long id);
    TicketResponse createTicket(TicketRequest request);
    TicketResponse updateTicket(Long id, TicketRequest request);
    TicketResponse updateTicketStatus(Long id, String status);
    void deleteTicket(Long id);
}