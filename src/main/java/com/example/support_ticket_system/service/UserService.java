package com.example.support_ticket_system.service;

import com.example.support_ticket_system.dto.UserRequest;
import com.example.support_ticket_system.dto.UserResponse;
import java.util.List;

public interface UserService {
    List<UserResponse> getAllUsers();
    UserResponse getUserById(Long id);
    UserResponse createUser(UserRequest request);
}