package com.polsl.yachtclubmanager.controllers;

import com.polsl.yachtclubmanager.models.dto.requests.ChangePasswordRequest;
import com.polsl.yachtclubmanager.models.dto.requests.UserByAdminRequest;
import com.polsl.yachtclubmanager.models.dto.requests.UserRequest;
import com.polsl.yachtclubmanager.models.dto.responses.UserDetailsResponse;
import com.polsl.yachtclubmanager.models.dto.responses.UserResponse;
import com.polsl.yachtclubmanager.models.dto.responses.UsersResponse;
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

    @GetMapping("/details")
    public ResponseEntity<UserDetailsResponse> getUserDetails(@RequestParam String userId) {
        return ResponseEntity.ok(service.getUserDetails(userId));
    }

    @PostMapping("/deactivate")
    public ResponseEntity<Boolean> deactivateUser(@RequestParam String userId) {
        return ResponseEntity.ok(service.deactivateUser(userId));
    }

    @PostMapping("/edit")
    public ResponseEntity<Boolean> editUserByAdmin(@RequestBody UserByAdminRequest userRequest) {
        return ResponseEntity.ok(service.editUserByAdmin(userRequest));
    }

    @PostMapping("/validate")
    public ResponseEntity<Boolean> validateUser(@RequestParam String userId) {
        return ResponseEntity.ok(service.validateUser(userId));
    }

    @PostMapping
    public ResponseEntity<UserResponse> editUser(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(service.editUser(userRequest));
    }

    @PostMapping("/changePassword")
    public ResponseEntity<Boolean> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        return ResponseEntity.ok(service.changePassword(changePasswordRequest));
    }
}
