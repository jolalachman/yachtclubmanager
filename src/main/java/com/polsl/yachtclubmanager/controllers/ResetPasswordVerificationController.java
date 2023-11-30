package com.polsl.yachtclubmanager.controllers;

import com.polsl.yachtclubmanager.models.dto.requests.ResetPasswordRequest;
import com.polsl.yachtclubmanager.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/resetPassword")
@RequiredArgsConstructor
public class ResetPasswordVerificationController {
    private final AuthenticationService service;

    @PostMapping
    public ResponseEntity<Boolean> generateResetPassword(@RequestBody ResetPasswordRequest request) {
        Boolean isSuccess = service.generateResetPasswordToken(request.getUserId());
        return ResponseEntity.ok(isSuccess);
    }
    @GetMapping
    public ResponseEntity<Boolean> confirmResetPassword(@RequestBody ResetPasswordRequest request) {
        Boolean isSuccess = service.verifyResetPasswordToken(request.getResetToken(), request.getNewPassword());
        return ResponseEntity.ok(isSuccess);
    }
}
