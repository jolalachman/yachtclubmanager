package com.polsl.yachtclubmanager.services;

import com.polsl.yachtclubmanager.config.JwtService;
import com.polsl.yachtclubmanager.models.dto.requests.AccountVerifyRequest;
import com.polsl.yachtclubmanager.models.dto.requests.AuthenticationRequest;
import com.polsl.yachtclubmanager.models.dto.responses.AuthenticationResponse;
import com.polsl.yachtclubmanager.models.dto.requests.RegisterRequest;
import com.polsl.yachtclubmanager.enums.RoleName;
import com.polsl.yachtclubmanager.models.entities.AccountVerification;
import com.polsl.yachtclubmanager.models.entities.ResetPasswordVerification;
import com.polsl.yachtclubmanager.repositories.*;
import lombok.RequiredArgsConstructor;
import com.polsl.yachtclubmanager.models.entities.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AccountVerificationRepository accountVerificationRepository;
    private final ResetPasswordVerificationRepository resetPasswordVerificationRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final EmailService emailService;
    private final SailingLicenseRepository sailingLicenseRepository;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .clubStatus(request.getClubStatus())
                .sailingLicense(sailingLicenseRepository.findBySailingLicenseId(request.getSailingLicense()))
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .enabled(false)
                .nonLocked(true)
                .role(roleRepository.findByRoleName(RoleName.CANDIDATE))
                .build();
        userRepository.save(user);

        AccountVerification accountVerification = new AccountVerification(user);
        accountVerificationRepository.save(accountVerification);

        emailService.sendAccountVerificationMail(user.getEmail(), accountVerification.getToken());

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .auth_token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .auth_token(jwtToken)
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .id(user.getUserId().toString())
                .build();
    }

    public String verifyToken(AccountVerifyRequest request) {
        AccountVerification accountVerification = accountVerificationRepository
                .findByToken(request.getToken()).orElseThrow();
            User user = userRepository.findByEmailIgnoreCase(accountVerification.getUser().getEmail());
            user.setEnabled(true);
            userRepository.save(user);
            accountVerificationRepository.delete(accountVerification);
        return Boolean.TRUE.toString();
    }

    public Boolean generateResetPasswordToken(String email) {
        User user = userRepository.findByEmailIgnoreCase(email);
        if(user.getEnabled()) {
            ResetPasswordVerification resetPasswordVerification = new ResetPasswordVerification(user);
            resetPasswordVerificationRepository.save(resetPasswordVerification);

            emailService.sendResetPasswordMail(user.getEmail(), resetPasswordVerification.getResetToken());
            return Boolean.TRUE;
        }
        else {
            return Boolean.FALSE;
        }
    }
    public Boolean verifyResetPasswordToken(String token, String newPassword) {
        ResetPasswordVerification resetPasswordVerification = resetPasswordVerificationRepository
                .findByResetToken(token).orElseThrow();
        if (resetPasswordVerification.getExpirationDate().before(new Date())) {
            return Boolean.FALSE;
        } else {
            User user = userRepository.findByEmailIgnoreCase(resetPasswordVerification.getUser().getEmail());
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            resetPasswordVerificationRepository.delete(resetPasswordVerification);
            return Boolean.TRUE;
        }
    }
}
