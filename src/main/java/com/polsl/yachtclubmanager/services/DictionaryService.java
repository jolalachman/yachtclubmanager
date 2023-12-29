package com.polsl.yachtclubmanager.services;

import com.polsl.yachtclubmanager.enums.RoleName;
import com.polsl.yachtclubmanager.enums.YachtStatusName;
import com.polsl.yachtclubmanager.models.dto.responses.DictionaryResponse;
import com.polsl.yachtclubmanager.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DictionaryService {
    private final YachtRepository yachtRepository;
    private final UserRepository userRepository;
    private final SailingLicenseRepository sailingLicenseRepository;
    private final ReservationRepository reservationRepository;
    private final NoticeStatusRepository noticeStatusRepository;

    public List<DictionaryResponse> getAllReservations() {
        var reservations = reservationRepository.findAll();
        List<DictionaryResponse> dictionaryResponses = reservations.stream()
                .map(reservation -> {
                    DictionaryResponse response = new DictionaryResponse();
                    response.setId(reservation.getReservationId());
                    response.setName("#"+reservation.getReservationId());
                    return response;
                })
                .collect(Collectors.toList());

        return dictionaryResponses;
    }
    public List<DictionaryResponse> getAllYachts() {
        var yachts = yachtRepository.findAll();
        List<DictionaryResponse> dictionaryResponses = yachts.stream()
                .map(yacht -> {
                    DictionaryResponse response = new DictionaryResponse();
                    response.setId(yacht.getYachtId());
                    response.setName(yacht.getName() + " - " + yacht.getRegistrationNumber());
                    return response;
                })
                .collect(Collectors.toList());

        return dictionaryResponses;
    }
    public List<DictionaryResponse> getYachts() {
        var yachts = yachtRepository.findAll();
        List<DictionaryResponse> dictionaryResponses = yachts.stream()
                .filter(yacht -> !(
                        yacht.getYachtStatus().getYachtStatusName().equals(YachtStatusName.IN_REPAIR) ||
                                yacht.getYachtStatus().getYachtStatusName().equals(YachtStatusName.MALFUNCTION) ||
                                yacht.getYachtStatus().getYachtStatusName().equals(YachtStatusName.DEACTIVATED)
                ))
                .map(yacht -> {
                    DictionaryResponse response = new DictionaryResponse();
                    response.setId(yacht.getYachtId());
                    response.setName(yacht.getName() + " - " + yacht.getRegistrationNumber());
                    return response;
                })
                .collect(Collectors.toList());

        return dictionaryResponses;
    }

    public List<DictionaryResponse> getUsers() {
        var users = userRepository.findAll();
        List<DictionaryResponse> dictionaryResponses = users.stream()
                .filter(user -> !(
                        user.getRole().getRoleName().equals(RoleName.ADMIN) ||
                                user.getRole().getRoleName().equals(RoleName.CANDIDATE) ||
                                user.getRole().getRoleName().equals(RoleName.DEACTIVATED)
                ))
                .map(user -> {
                    DictionaryResponse response = new DictionaryResponse();
                    response.setId(user.getUserId());
                    response.setName(user.getFirstName() + " " + user.getLastName());
                    return response;
                })
                .collect(Collectors.toList());

        return dictionaryResponses;
    }

    public List<DictionaryResponse> getClubMembers() {
        var users = userRepository.findAll();
        List<DictionaryResponse> dictionaryResponses = users.stream()
                .filter(user -> !(
                        user.getRole().getRoleName().equals(RoleName.ADMIN) ||
                                user.getRole().getRoleName().equals(RoleName.CANDIDATE)
                ))
                .map(user -> {
                    DictionaryResponse response = new DictionaryResponse();
                    response.setId(user.getUserId());
                    response.setName(user.getFirstName() + " " + user.getLastName());
                    return response;
                })
                .collect(Collectors.toList());

        return dictionaryResponses;
    }

    public List<DictionaryResponse> getReservingUsers() {
        var users = userRepository.findAll();
        List<DictionaryResponse> dictionaryResponses = users.stream()
                .filter(user -> (
                        user.getRole().getRoleName().equals(RoleName.BOSMAN)
                ))
                .map(user -> {
                    DictionaryResponse response = new DictionaryResponse();
                    response.setId(user.getUserId());
                    response.setName(user.getFirstName() + " " + user.getLastName());
                    return response;
                })
                .collect(Collectors.toList());

        return dictionaryResponses;
    }

    public List<DictionaryResponse> getSailingLicenses() {
        var licenses = sailingLicenseRepository.findAll();
        List<DictionaryResponse> dictionaryResponses = licenses.stream()
                .map(license -> {
                    DictionaryResponse response = new DictionaryResponse();
                    response.setId(license.getSailingLicenseId());
                    response.setName(license.getSailingLicenseName().toString());
                    return response;
                })
                .collect(Collectors.toList());

        return dictionaryResponses;
    }

    public List<DictionaryResponse> getNoticeStatuses() {
        var statuses = noticeStatusRepository.findAll();
        List<DictionaryResponse> dictionaryResponses = statuses.stream()
                .map(status -> {
                    DictionaryResponse response = new DictionaryResponse();
                    response.setId(status.getNoticeStatusId());
                    response.setName(status.getNoticeStatusName().toString());
                    return response;
                })
                .collect(Collectors.toList());

        return dictionaryResponses;
    }
}
