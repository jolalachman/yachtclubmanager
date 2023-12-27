package com.polsl.yachtclubmanager.controllers;

import com.polsl.yachtclubmanager.models.dto.responses.UserResponse;
import com.polsl.yachtclubmanager.models.dto.responses.UsersListResponse;
import com.polsl.yachtclubmanager.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UsersController {
    private final UserService service;
    @GetMapping
    public ResponseEntity<UsersListResponse> getAllUsers() {
        return ResponseEntity.ok(service.getAllUsers());
    }

    @GetMapping("/club-members")
    public ResponseEntity<UsersListResponse> getAllClubMembers() {
        return ResponseEntity.ok(service.getAllClubMembers());
    }
}
