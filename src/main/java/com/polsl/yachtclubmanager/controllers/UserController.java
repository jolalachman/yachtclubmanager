package com.polsl.yachtclubmanager.controllers;

import com.polsl.yachtclubmanager.models.dto.requests.UserRequest;
import com.polsl.yachtclubmanager.models.dto.responses.UserResponse;
import com.polsl.yachtclubmanager.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    @GetMapping
    public ResponseEntity<UserResponse> getUser(@RequestParam String userId) {
        return ResponseEntity.ok(service.getUser(userId));
    }

    @PatchMapping
    public ResponseEntity<UserResponse> editUser(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(service.editUser(userRequest));
    }
}
