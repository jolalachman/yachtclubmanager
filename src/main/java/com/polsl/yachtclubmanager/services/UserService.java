package com.polsl.yachtclubmanager.services;

import com.polsl.yachtclubmanager.enums.ReservationStatusName;
import com.polsl.yachtclubmanager.enums.RoleName;
import com.polsl.yachtclubmanager.enums.SailingLicenseName;
import com.polsl.yachtclubmanager.enums.YachtStatusName;
import com.polsl.yachtclubmanager.models.dto.other.PageInfo;
import com.polsl.yachtclubmanager.models.dto.requests.ChangePasswordRequest;
import com.polsl.yachtclubmanager.models.dto.requests.UserByAdminRequest;
import com.polsl.yachtclubmanager.models.dto.requests.UserRequest;
import com.polsl.yachtclubmanager.models.dto.responses.*;
import com.polsl.yachtclubmanager.models.entities.Reservation;
import com.polsl.yachtclubmanager.models.entities.Role;
import com.polsl.yachtclubmanager.models.entities.User;
import com.polsl.yachtclubmanager.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final SailingLicenseRepository sailingLicenseRepository;
    private final ReservationRepository reservationRepository;
    private final RoleRepository roleRepository;
    private final ReservationStatusRepository reservationStatusRepository;
    private final EmailService emailService;

    public UsersListResponse getAllUsers() {
        var users = userRepository.findAll();
        var items = users.stream()
                .filter(user -> !user.getRole().getRoleName().equals(RoleName.DEACTIVATED))
                .map(user -> mapToUsersResponse(user))
                .collect(Collectors.toList());
        var pageInfo = PageInfo.builder()
                .offset(0L)
                .limit(10L)
                .build();
        var totalCount = items.size();
        return UsersListResponse.builder()
                .items(items)
                .pageInfo(pageInfo)
                .totalCount(totalCount)
                .build();
    }

    public UsersListResponse getAllClubMembers() {
        var users = userRepository.findAll();
        var items = users.stream()
                .filter(user -> !user.getRole().getRoleName().equals(RoleName.DEACTIVATED) && !user.getRole().getRoleName().equals(RoleName.ADMIN))
                .map(user -> mapToUsersResponse(user))
                .collect(Collectors.toList());
        var pageInfo = PageInfo.builder()
                .offset(0L)
                .limit(10L)
                .build();
        var totalCount = items.size();
        return UsersListResponse.builder()
                .items(items)
                .pageInfo(pageInfo)
                .totalCount(totalCount)
                .build();
    }

    public UserResponse getUser(String userId) {
        var user = userRepository.findByUserId(Long.parseLong(userId));
        var sailingLicence = DictionaryResponse.builder()
                .id(user.getSailingLicense().getSailingLicenseId())
                .name(user.getSailingLicense().getSailingLicenseName().toString())
                .build();
        return UserResponse.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .clubStatus(user.getClubStatus())
                .sailingLicense(sailingLicence)
                .build();
    }

    public UserDetailsResponse getUserDetails(String userId) {
        var user = userRepository.findByUserId(Long.parseLong(userId));
        var reservations = reservationRepository.findAllByUser(user);
        var items = reservations.stream()
                .map(reservation -> mapToReservationResponse(reservation))
                .collect(Collectors.toList());
        var role = DictionaryResponse.builder()
                .id(user.getRole().getRoleId())
                .name(user.getRole().getRoleName().toString())
                .build();
        var sailingLicense = DictionaryResponse.builder()
                .id(user.getSailingLicense().getSailingLicenseId())
                .name(user.getSailingLicense().getSailingLicenseName().toString())
                .build();
        return UserDetailsResponse.builder()
                .id(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(role)
                .clubStatus(user.getClubStatus())
                .sailingLicense(sailingLicense)
                .reservations(items)
                .build();
    }

    public UserResponse editUser(UserRequest userRequest) {
        var user = userRepository.findByUserId(Long.parseLong(userRequest.getId()));
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setPhone(userRequest.getPhone());
        user.setClubStatus(userRequest.getClubStatus());
        user.setSailingLicense(sailingLicenseRepository.findBySailingLicenseId(userRequest.getSailingLicenseId()));
        userRepository.save(user);
        var sailingLicence = DictionaryResponse.builder()
                .id(user.getSailingLicense().getSailingLicenseId())
                .name(user.getSailingLicense().getSailingLicenseName().toString())
                .build();
        return UserResponse.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .clubStatus(user.getClubStatus())
                .sailingLicense(sailingLicence)
                .build();
    }

    public Boolean editUserByAdmin(UserByAdminRequest userRequest) {
        var user = userRepository.findByUserId(userRequest.getId());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setRole(roleRepository.findByRoleId(Long.parseLong(userRequest.getRole())));
        user.setClubStatus(userRequest.getClubStatus());
        user.setSailingLicense(sailingLicenseRepository.findBySailingLicenseId(userRequest.getSailingLicense()));
        userRepository.save(user);
        return Boolean.TRUE;
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
    public Boolean deactivateUser(String userId) {
        var user = userRepository.findByUserId(Long.parseLong(userId));
        if (user.getRole().getRoleName().equals(RoleName.ADMIN) || user.getRole().getRoleName().equals(RoleName.DEACTIVATED)) {
            return Boolean.FALSE;
        }
        else {
            var reservations = reservationRepository.findAllByUser(user);
            reservations.stream()
                    .filter(reservation -> reservation.getReservationStatus().getReservationStatusName().equals(ReservationStatusName.PENDING) ||
                        reservation.getReservationStatus().getReservationStatusName().equals(ReservationStatusName.CONFIRMED))
                    .map(this::mapToCancelledStatus)
                    .collect(Collectors.toList());
            user.setFirstName("-");
            user.setLastName("-");
            user.setPhone(null);
            user.setClubStatus(null);
            user.setSailingLicense(sailingLicenseRepository.findBySailingLicenseId(1L));
            user.setNonLocked(false);
            user.setRole(roleRepository.findByRoleName(RoleName.DEACTIVATED));
            userRepository.save(user);
            emailService.sendUserDeactivated(user.getEmail());
            return Boolean.TRUE;
        }
    }
    public Boolean validateUser(String userId) {
        var user = userRepository.findByUserId(Long.parseLong(userId));
        if (user.getRole().getRoleName().equals(RoleName.CANDIDATE)) {
            user.setRole(roleRepository.findByRoleName(RoleName.SAILOR));
            userRepository.save(user);
            return Boolean.TRUE;
        }
        else {
            return Boolean.FALSE;
        }
    }
    private UsersResponse mapToUsersResponse(User user) {
        var role = DictionaryResponse.builder()
                .id(user.getRole().getRoleId())
                .name(user.getRole().getRoleName().toString())
                .build();
        var sailingLicence = DictionaryResponse.builder()
                .id(user.getSailingLicense().getSailingLicenseId())
                .name(user.getSailingLicense().getSailingLicenseName().toString())
                .build();
        return UsersResponse.builder()
                .id(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(role)
                .clubStatus(user.getClubStatus())
                .sailingLicense(sailingLicence)
                .build();
    }

    private Reservation mapToCancelledStatus(Reservation reservation) {
        if (!reservation.getReservationStatus().getReservationStatusName().equals(ReservationStatusName.RES_COMPLETED)) {
            reservation.setReservationStatus(reservationStatusRepository.findByReservationStatusName(ReservationStatusName.CANCELLED));
            reservationRepository.save(reservation);
        }
        return reservation;
    }

    private ReservationResponse mapToReservationResponse(Reservation reservation) {
        return ReservationResponse.builder()
                .id(reservation.getReservationId())
                .pickupDate(reservation.getPickup())
                .dropoffDate(reservation.getDropoff())
                .clientInfo(reservation.getUser().getFirstName() + " " + reservation.getUser().getLastName())
                .currentStatus(reservation.getReservationStatus().getReservationStatusName().toString())
                .yachtId(reservation.getYacht().getYachtId())
                .build();
    }
}
