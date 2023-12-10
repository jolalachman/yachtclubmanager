package com.polsl.yachtclubmanager.services;

import com.polsl.yachtclubmanager.models.dto.requests.ChangePasswordRequest;
import com.polsl.yachtclubmanager.models.dto.requests.UserRequest;
import com.polsl.yachtclubmanager.models.dto.responses.AuthenticationResponse;
import com.polsl.yachtclubmanager.models.dto.responses.UserResponse;
import com.polsl.yachtclubmanager.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    public UserResponse getUser(String userId) {
        var user = userRepository.findByUserId(Long.parseLong(userId));
        return UserResponse.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .clubStatus(user.getClubStatus())
                .sailingLicense(user.getSailingLicense())
                .build();
    }

    public UserResponse editUser(UserRequest userRequest) {
        var user = userRepository.findByUserId(Long.parseLong(userRequest.getId()));
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setPhone(userRequest.getPhone());
        user.setClubStatus(userRequest.getClubStatus());
        user.setSailingLicense(userRequest.getSailingLicense());
        userRepository.save(user);
        return UserResponse.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .clubStatus(user.getClubStatus())
                .sailingLicense(user.getSailingLicense())
                .build();
    }

    public Boolean changePassword(ChangePasswordRequest changePasswordRequest) {
        var user = userRepository.findByUserId(Long.parseLong(changePasswordRequest.getId()));
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        changePasswordRequest.getOldPassword()
                )
        );
        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        userRepository.save(user);
        return Boolean.TRUE;
    }
}
