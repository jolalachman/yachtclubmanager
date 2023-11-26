package com.polsl.yachtclubmanager.services;

import com.polsl.yachtclubmanager.config.JwtService;
import com.polsl.yachtclubmanager.models.dto.requests.AuthenticationRequest;
import com.polsl.yachtclubmanager.models.dto.responses.AuthenticationResponse;
import com.polsl.yachtclubmanager.models.dto.requests.RegisterRequest;
import com.polsl.yachtclubmanager.enums.RoleName;
import com.polsl.yachtclubmanager.models.entities.Role;
import com.polsl.yachtclubmanager.repositories.RoleRepository;
import com.polsl.yachtclubmanager.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import com.polsl.yachtclubmanager.models.entities.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .enabled(false)
                .nonLocked(true)
//                .roleName(RoleName.CANDIDATE)
                .role(roleRepository.findByRoleName(RoleName.CANDIDATE))
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
