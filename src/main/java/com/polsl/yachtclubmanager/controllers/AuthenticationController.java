package com.polsl.yachtclubmanager.controllers;

import com.polsl.yachtclubmanager.models.dto.requests.AccountVerifyRequest;
import com.polsl.yachtclubmanager.models.dto.requests.AuthenticationRequest;
import com.polsl.yachtclubmanager.models.dto.requests.RegisterRequest;
import com.polsl.yachtclubmanager.models.dto.responses.AuthenticationResponse;
import com.polsl.yachtclubmanager.models.entities.AccountVerification;
import com.polsl.yachtclubmanager.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
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


}
