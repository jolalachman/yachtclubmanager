package com.polsl.yachtclubmanager.services;

import com.polsl.yachtclubmanager.enums.ReservationStatusName;
import com.polsl.yachtclubmanager.models.dto.other.PageInfo;
import com.polsl.yachtclubmanager.models.dto.requests.ReservationRequest;
import com.polsl.yachtclubmanager.models.dto.responses.ReservationsListResponse;
import com.polsl.yachtclubmanager.models.dto.responses.ReservationsResponse;
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

    private ReservationsResponse mapToReservationResponse(Reservation reservation) {
        return ReservationsResponse.builder()
                .id(reservation.getReservationId())
                .peopleNumber(reservation.getPeopleNumber())
                .pickupDate(reservation.getPickup())
                .dropoffDate(reservation.getDropoff())
                .yachtName(reservation.getYacht().getName())
                .yachtId(reservation.getYacht().getYachtId())
                .reservingPerson(reservation.getReservingPerson())
                .clientInfo(reservation.getUser().getFirstName() + " " + reservation.getUser().getLastName())
                .currentStatus(reservation.getReservationStatus().getReservationStatusName().toString())
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
                    .reservingPerson(reservationRequest.getReservingPerson())
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
                    .reservingPerson(reservationRequest.getReservingPerson())
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
}
