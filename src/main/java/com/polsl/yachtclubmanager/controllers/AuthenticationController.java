package com.polsl.yachtclubmanager.controllers;

import com.polsl.yachtclubmanager.models.dto.requests.*;
import com.polsl.yachtclubmanager.models.dto.responses.AuthenticationResponse;
import com.polsl.yachtclubmanager.models.dto.responses.DictionaryResponse;
import com.polsl.yachtclubmanager.services.AuthenticationService;
import com.polsl.yachtclubmanager.services.DictionaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final DictionaryService dictionaryService;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PatchMapping("/confirmUser")
    public ResponseEntity<String> confirmUserAccount(@RequestBody AccountVerifyRequest request) {
        String isSuccess = service.verifyToken(request);
        return ResponseEntity.ok(isSuccess);
    }

    @PostMapping("/recoverPassword")
    public ResponseEntity<Boolean> generateResetPassword(@RequestBody RecoverPasswordRequest request) {
        Boolean isSuccess = service.generateResetPasswordToken(request.getEmail());
        return ResponseEntity.ok(isSuccess);
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<Boolean> confirmResetPassword(@RequestBody ResetPasswordRequest request) {
        Boolean isSuccess = service.verifyResetPasswordToken(request.getResetToken(), request.getNewPassword());
        return ResponseEntity.ok(isSuccess);
    }

    @GetMapping("/sailingLicenses")
    public ResponseEntity<List<DictionaryResponse>> getSailingLicenses() {
        return ResponseEntity.ok(dictionaryService.getSailingLicenses());
    }
}
