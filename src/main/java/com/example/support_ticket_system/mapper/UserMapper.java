package com.example.support_ticket_system.mapper;

import com.example.support_ticket_system.domain.entity.User;
import com.example.support_ticket_system.dto.UserResponse;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole().name());
        return response;
    }
}