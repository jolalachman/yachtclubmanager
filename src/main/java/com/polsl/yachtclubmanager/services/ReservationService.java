package com.polsl.yachtclubmanager.services;

import com.polsl.yachtclubmanager.enums.ReservationStatusName;
import com.polsl.yachtclubmanager.models.dto.other.PageInfo;
import com.polsl.yachtclubmanager.models.dto.requests.ReservationRequest;
import com.polsl.yachtclubmanager.models.dto.responses.*;
import com.polsl.yachtclubmanager.models.entities.Reservation;
import com.polsl.yachtclubmanager.repositories.ReservationRepository;
import com.polsl.yachtclubmanager.repositories.ReservationStatusRepository;
import com.polsl.yachtclubmanager.repositories.UserRepository;
import com.polsl.yachtclubmanager.repositories.YachtRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationStatusRepository reservationStatusRepository;
    private final UserRepository userRepository;
    private final YachtRepository yachtRepository;

    public ReservationsListResponse getReservations() {
        var reservations = reservationRepository.findAll();
        var items = reservations.stream()
                .filter(reservation -> !reservation.getReservationStatus().getReservationStatusName().equals(ReservationStatusName.CANCELLED) ||
                        !reservation.getReservationStatus().getReservationStatusName().equals(ReservationStatusName.REJECTED))
                .map(this::mapToReservationResponse)
                .collect(Collectors.toList());
        var pageInfo = PageInfo.builder()
                .offset(0L)
                .limit(10L)
                .build();
        var totalCount = items.size();

        return ReservationsListResponse.builder()
                .items(items)
                .pageInfo(pageInfo)
                .totalCount(totalCount)
                .build();
    }

    public MyReservationsListResponse getMyReservations(String userId) {
        var reservations = reservationRepository.findAll();
        var items = reservations.stream()
                .filter(reservation -> reservation.getUser().getUserId().toString().equals(userId))
                .map(this::mapToMyReservationResponse)
                .collect(Collectors.toList());
        var pageInfo = PageInfo.builder()
                .offset(0L)
                .limit(10L)
                .build();
        var totalCount = items.size();

        return MyReservationsListResponse.builder()
                .items(items)
                .pageInfo(pageInfo)
                .totalCount(totalCount)
                .build();
    }

    private ReservationsResponse mapToReservationResponse(Reservation reservation) {
        var currentStatus = DictionaryResponse.builder()
                .id(reservation.getReservationStatus().getReservationStatusId())
                .name(reservation.getReservationStatus().getReservationStatusName().toString())
                .build();
        return ReservationsResponse.builder()
                .id(reservation.getReservationId())
                .peopleNumber(reservation.getPeopleNumber())
                .pickupDate(reservation.getPickup())
                .dropoffDate(reservation.getDropoff())
                .yachtName(reservation.getYacht().getName())
                .yachtId(reservation.getYacht().getYachtId())
                .reservingPerson(reservation.getReservingPerson().getFirstName() + " " + reservation.getReservingPerson().getLastName())
                .clientInfo(reservation.getUser().getFirstName() + " " + reservation.getUser().getLastName())
                .currentStatus(currentStatus)
                .photo(reservation.getYacht().getPhoto())
                .build();
    }

    public Boolean addReservation(ReservationRequest reservationRequest) {
        try {
            LocalDateTime pickupDateTime = convertToLocalDateTime(reservationRequest.getPickup());
            LocalDateTime dropoffDateTime = convertToLocalDateTime(reservationRequest.getDropoff());

            Reservation reservation = Reservation.builder()
                    .pickup(pickupDateTime)
                    .dropoff(dropoffDateTime)
                    .peopleNumber(reservationRequest.getPeopleNumber())
                    .reservationStatus(reservationStatusRepository.findByReservationStatusName(ReservationStatusName.CONFIRMED))
                    .reservingPerson(userRepository.findByUserId(Long.parseLong(reservationRequest.getReservingPersonId())))
                    .user(userRepository.findByUserId(reservationRequest.getUserId()))
                    .yacht(yachtRepository.findByYachtId(reservationRequest.getYachtId()))
                    .build();

            reservationRepository.save(reservation);
            return Boolean.TRUE;
        } catch (DateTimeParseException e) {
            // Handle the exception - log or throw a custom exception
            e.printStackTrace(); // Logging the exception for now
            return Boolean.FALSE; // Indicate failure due to date parsing error
        }
    }

    public Boolean addYachtReservation(ReservationRequest reservationRequest) {
        try {
            LocalDateTime pickupDateTime = convertToLocalDateTime(reservationRequest.getPickup());
            LocalDateTime dropoffDateTime = convertToLocalDateTime(reservationRequest.getDropoff());

            Reservation reservation = Reservation.builder()
                    .pickup(pickupDateTime)
                    .dropoff(dropoffDateTime)
                    .peopleNumber(reservationRequest.getPeopleNumber())
                    .reservationStatus(reservationStatusRepository.findByReservationStatusName(ReservationStatusName.PENDING))
                    .reservingPerson(userRepository.findByUserId(Long.parseLong(reservationRequest.getReservingPersonId())))
                    .user(userRepository.findByUserId(reservationRequest.getUserId()))
                    .yacht(yachtRepository.findByYachtId(reservationRequest.getYachtId()))
                    .build();

            reservationRepository.save(reservation);
            return Boolean.TRUE;
        } catch (DateTimeParseException e) {
            // Handle the exception - log or throw a custom exception
            e.printStackTrace(); // Logging the exception for now
            return Boolean.FALSE; // Indicate failure due to date parsing error
        }
    }

    private LocalDateTime convertToLocalDateTime(String isoDateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        return LocalDateTime.parse(isoDateString, formatter);
    }

    private MyReservationsResponse mapToMyReservationResponse(Reservation reservation) {
        var currentStatus = DictionaryResponse.builder()
                .id(reservation.getReservationStatus().getReservationStatusId())
                .name(reservation.getReservationStatus().getReservationStatusName().toString())
                .build();
        return MyReservationsResponse.builder()
                .id(reservation.getReservationId())
                .peopleNumber(reservation.getPeopleNumber())
                .pickupDate(reservation.getPickup())
                .dropoffDate(reservation.getDropoff())
                .yachtName(reservation.getYacht().getName())
                .yachtId(reservation.getYacht().getYachtId())
                .currentStatus(currentStatus)
                .photo(reservation.getYacht().getPhoto())
                .build();
    }

}
