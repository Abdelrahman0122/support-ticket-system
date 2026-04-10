package com.example.support_ticket_system.service.impl;

import com.example.support_ticket_system.domain.entity.Ticket;
import com.example.support_ticket_system.domain.entity.User;
import com.example.support_ticket_system.domain.entity.enums.Priority;
import com.example.support_ticket_system.domain.entity.enums.Role;
import com.example.support_ticket_system.domain.entity.enums.TicketStatus;
import com.example.support_ticket_system.dto.TicketRequest;
import com.example.support_ticket_system.dto.TicketResponse;
import com.example.support_ticket_system.mapper.UserMapper;
import com.example.support_ticket_system.repository.TicketRepository;
import com.example.support_ticket_system.repository.UserRepository;
import com.example.support_ticket_system.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private boolean isAdminOrAgent(User user) {
        return user.getRole() == Role.ADMIN || user.getRole() == Role.AGENT;
    }

    private TicketResponse mapToResponse(Ticket ticket) {
        TicketResponse response = new TicketResponse();
        response.setId(ticket.getId());
        response.setTitle(ticket.getTitle());
        response.setDescription(ticket.getDescription());
        response.setStatus(ticket.getStatus().name());
        response.setPriority(ticket.getPriority().name());
        response.setAssignedTo(ticket.getAssignedTo() != null ? userMapper.toResponse(ticket.getAssignedTo()) : null);
        response.setCreatedBy(userMapper.toResponse(ticket.getCreatedBy()));
        response.setCreatedAt(ticket.getCreatedAt());
        response.setUpdatedAt(ticket.getUpdatedAt());
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TicketResponse> getAllTickets() {
        User currentUser = getCurrentUser();
        List<Ticket> tickets;

        if (currentUser.getRole() == Role.ADMIN) {
            tickets = ticketRepository.findAll();
        } else if (currentUser.getRole() == Role.AGENT) {
            tickets = ticketRepository.findByCreatorOrAssignee(currentUser.getId());
        } else {
            tickets = ticketRepository.findByCreatedById(currentUser.getId());
        }

        return tickets.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TicketResponse> getMyTickets() {
        User currentUser = getCurrentUser();
        List<Ticket> tickets = ticketRepository.findByCreatedById(currentUser.getId());
        return tickets.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TicketResponse> getAssignedTickets() {
        User currentUser = getCurrentUser();
        if (!isAdminOrAgent(currentUser)) {
            throw new RuntimeException("Access denied: Only agents and admins can view assigned tickets");
        }

        List<Ticket> tickets = ticketRepository.findByAssignedToId(currentUser.getId());
        return tickets.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public TicketResponse getTicketById(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        User currentUser = getCurrentUser();

        // Users can only see their own tickets, agents/admins can see tickets they're assigned to or created
        boolean canAccess = ticket.getCreatedBy().getId().equals(currentUser.getId()) ||
                           (ticket.getAssignedTo() != null && ticket.getAssignedTo().getId().equals(currentUser.getId())) ||
                           isAdminOrAgent(currentUser);

        if (!canAccess) {
            throw new RuntimeException("Access denied: You don't have permission to view this ticket");
        }

        return mapToResponse(ticket);
    }

    @Override
    public TicketResponse createTicket(TicketRequest request) {
        User currentUser = getCurrentUser();

        Ticket ticket = new Ticket();
        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());
        ticket.setStatus(TicketStatus.OPEN);
        ticket.setPriority(Priority.valueOf(request.getPriority().toUpperCase()));
        ticket.setCreatedBy(currentUser);
        ticket.setCreatedAt(LocalDateTime.now());
        ticket.setUpdatedAt(LocalDateTime.now());

        // Assign ticket if assignedToId is provided and user is admin/agent
        if (request.getAssignedToId() != null) {
            if (!isAdminOrAgent(currentUser)) {
                throw new RuntimeException("Access denied: Only agents and admins can assign tickets");
            }

            User assignedUser = userRepository.findById(request.getAssignedToId())
                    .orElseThrow(() -> new RuntimeException("Assigned user not found"));
            ticket.setAssignedTo(assignedUser);
        }

        Ticket savedTicket = ticketRepository.save(ticket);
        return mapToResponse(savedTicket);
    }

    @Override
    public TicketResponse updateTicket(Long id, TicketRequest request) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        User currentUser = getCurrentUser();

        // Only ticket creator or assigned agent/admin can update
        boolean canUpdate = ticket.getCreatedBy().getId().equals(currentUser.getId()) ||
                           (ticket.getAssignedTo() != null && ticket.getAssignedTo().getId().equals(currentUser.getId())) ||
                           isAdminOrAgent(currentUser);

        if (!canUpdate) {
            throw new RuntimeException("Access denied: You don't have permission to update this ticket");
        }

        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());
        ticket.setPriority(Priority.valueOf(request.getPriority().toUpperCase()));
        ticket.setUpdatedAt(LocalDateTime.now());

        // Update assignment if provided and user has permission
        if (request.getAssignedToId() != null) {
            if (!isAdminOrAgent(currentUser)) {
                throw new RuntimeException("Access denied: Only agents and admins can assign tickets");
            }

            User assignedUser = userRepository.findById(request.getAssignedToId())
                    .orElseThrow(() -> new RuntimeException("Assigned user not found"));
            ticket.setAssignedTo(assignedUser);
        }

        Ticket updatedTicket = ticketRepository.save(ticket);
        return mapToResponse(updatedTicket);
    }

    @Override
    public TicketResponse updateTicketStatus(Long id, String status) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        User currentUser = getCurrentUser();

        // Only assigned agent/admin can update status
        boolean canUpdate = (ticket.getAssignedTo() != null && ticket.getAssignedTo().getId().equals(currentUser.getId())) ||
                           isAdminOrAgent(currentUser);

        if (!canUpdate) {
            throw new RuntimeException("Access denied: Only assigned agents and admins can update ticket status");
        }

        ticket.setStatus(TicketStatus.valueOf(status.toUpperCase()));
        ticket.setUpdatedAt(LocalDateTime.now());

        Ticket updatedTicket = ticketRepository.save(ticket);
        return mapToResponse(updatedTicket);
    }

    @Override
    public void deleteTicket(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        User currentUser = getCurrentUser();

        // Only ticket creator or admin can delete
        boolean canDelete = ticket.getCreatedBy().getId().equals(currentUser.getId()) ||
                           currentUser.getRole() == Role.ADMIN;

        if (!canDelete) {
            throw new RuntimeException("Access denied: You don't have permission to delete this ticket");
        }

        ticketRepository.delete(ticket);
    }
}