package com.polsl.yachtclubmanager.services;

import com.polsl.yachtclubmanager.enums.ReservationStatusName;
import com.polsl.yachtclubmanager.models.dto.other.PageInfo;
import com.polsl.yachtclubmanager.models.dto.requests.ReservationRequest;
import com.polsl.yachtclubmanager.models.dto.responses.*;
import com.polsl.yachtclubmanager.models.entities.Reservation;
import com.polsl.yachtclubmanager.models.entities.ReservationStatusHistory;
import com.polsl.yachtclubmanager.models.entities.YachtStatusHistory;
import com.polsl.yachtclubmanager.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationStatusRepository reservationStatusRepository;
    private final UserRepository userRepository;
    private final YachtRepository yachtRepository;
    private final ReservationStatusHistoryRepository reservationStatusHistoryRepository;

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

    public ReservationDetailsResponse getReservationDetails(String reservationId) {
        var reservation = reservationRepository.findByReservationId(Long.parseLong(reservationId));
        var yacht = DictionaryResponse.builder()
                .id(reservation.getYacht().getYachtId())
                .name(reservation.getYacht().getName())
                .build();
        var reservingPerson = DictionaryResponse.builder()
                .id(reservation.getReservingPerson().getUserId())
                .name(reservation.getReservingPerson().getFirstName() + " " + reservation.getReservingPerson().getLastName())
                .build();

        var clientInfo = DictionaryResponse.builder()
                .id(reservation.getUser().getUserId())
                .name(reservation.getUser().getFirstName() + " " + reservation.getUser().getLastName())
                .build();

        var currentStatus = DictionaryResponse.builder()
                .id(reservation.getReservationStatus().getReservationStatusId())
                .name(reservation.getReservationStatus().getReservationStatusName().toString())
                .build();

        var canReport = false;

        if(reservation.getReservationStatus().getReservationStatusName().equals(ReservationStatusName.COMPLETED) && reservation.getDropoff().isBefore(LocalDateTime.now())) {
            canReport = true;
        }

        return ReservationDetailsResponse.builder()
                .id(reservation.getReservationId())
                .peopleNumber(reservation.getPeopleNumber())
                .pickupDate(reservation.getPickup())
                .dropoffDate(reservation.getDropoff())
                .yacht(yacht)
                .reservingPerson(reservingPerson)
                .clientInfo(clientInfo)
                .currentStatus(currentStatus)
                .photo(reservation.getYacht().getPhoto())
                .canReportNotice(canReport)
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

            var reservationHistory = ReservationStatusHistory.builder()
                    .reservationStatusHistoryDate(LocalDateTime.now())
                    .reservationStatus(reservation.getReservationStatus())
                    .reservation(reservation)
                    .build();
            reservationStatusHistoryRepository.save(reservationHistory);
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

            var reservationHistory = ReservationStatusHistory.builder()
                    .reservationStatusHistoryDate(LocalDateTime.now())
                    .reservationStatus(reservation.getReservationStatus())
                    .reservation(reservation)
                    .build();
            reservationStatusHistoryRepository.save(reservationHistory);
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

//    public changeReservationStatus() {
//        var reservationHistory = ReservationStatusHistory.builder()
//                .reservationStatusHistoryDate(LocalDateTime.now())
//                .reservationStatus(reservation.getReservationStatus())
//                .reservation(reservation)
//                .build();
//        reservationStatusHistoryRepository.save(reservationHistory);
//    }

    public List<ReservationStatusHistoryResponse> getReservationStatusHistory(Long reservationId) {
        var reservation = reservationRepository.findByReservationId(reservationId);
        var reservationStatusHistory = reservationStatusHistoryRepository.findAllByReservation(reservation);
        var items = reservationStatusHistory.stream()
                .map(this::mapToStatusHistoryResponse)
                .collect(Collectors.toList());
        return items;
    }

    private ReservationStatusHistoryResponse mapToStatusHistoryResponse(ReservationStatusHistory reservationStatusHistory) {
        return ReservationStatusHistoryResponse.builder()
                .statusDate(reservationStatusHistory.getReservationStatusHistoryDate())
                .statusName(reservationStatusHistory.getReservationStatus().getReservationStatusName().toString())
                .build();
    }


}
