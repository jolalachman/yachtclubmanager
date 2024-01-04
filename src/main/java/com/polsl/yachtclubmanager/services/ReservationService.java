package com.polsl.yachtclubmanager.services;

import com.polsl.yachtclubmanager.enums.NoticeStatusName;
import com.polsl.yachtclubmanager.enums.ReservationStatusName;
import com.polsl.yachtclubmanager.enums.YachtStatusName;
import com.polsl.yachtclubmanager.models.dto.other.PageInfo;
import com.polsl.yachtclubmanager.models.dto.requests.ChangeStatusRequest;
import com.polsl.yachtclubmanager.models.dto.requests.ReportReservationNoticeRequest;
import com.polsl.yachtclubmanager.models.dto.requests.ReservationRequest;
import com.polsl.yachtclubmanager.models.dto.responses.*;
import com.polsl.yachtclubmanager.models.entities.Notice;
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
    private final TechnicalDataRepository technicalDataRepository;
    private final NoticeRepository noticeRepository;
    private final NoticeStatusRepository noticeStatusRepository;
    private final YachtStatusRepository yachtStatusRepository;
    private final YachtStatusHistoryRepository yachtStatusHistoryRepository;

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
        var notices = noticeRepository.findAllByReservation(reservation);
        var canReport = (reservation.getReservationStatus().getReservationStatusName().equals(ReservationStatusName.RES_COMPLETED) && (reservation.getDropoff().isBefore(LocalDateTime.now()) && LocalDateTime.now().isBefore(reservation.getDropoff().plusDays(2))) && notices.size() == 0);
        var maxYachtPeople = technicalDataRepository.findByYacht(reservation.getYacht()).getMaxPeople();
        var showPeopleNumberWarning = (maxYachtPeople != null) && (reservation.getPeopleNumber() > maxYachtPeople) && (reservation.getReservationStatus().getReservationStatusName().equals(ReservationStatusName.PENDING));

        return ReservationDetailsResponse.builder()
                .id(reservation.getReservationId())
                .peopleNumber(reservation.getPeopleNumber())
                .pickupDate(reservation.getPickup())
                .dropoffDate(reservation.getDropoff())
                .yacht(yacht)
                .yachtStatus(reservation.getYacht().getYachtStatus().getYachtStatusName().toString())
                .reservingPerson(reservingPerson)
                .clientInfo(clientInfo)
                .currentStatus(currentStatus)
                .photo(reservation.getYacht().getPhoto())
                .canReportNotice(canReport)
                .showPeopleNumberWarning(showPeopleNumberWarning)
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

    public Boolean changeReservationStatus(ChangeStatusRequest request) {
        var reservation = reservationRepository.findByReservationId(request.getId());
        reservation.setReservationStatus(reservationStatusRepository.findByReservationStatusName(ReservationStatusName.valueOf(request.getStatus())));
        reservationRepository.save(reservation);
        var reservationHistory = ReservationStatusHistory.builder()
                .reservationStatusHistoryDate(LocalDateTime.now())
                .reservationStatus(reservation.getReservationStatus())
                .reservation(reservation)
                .build();
        reservationStatusHistoryRepository.save(reservationHistory);
        return Boolean.TRUE;
    }

    public Boolean giveReservation(String reservationId) {
        var reservation = reservationRepository.findByReservationId(Long.parseLong(reservationId));
        reservation.setReservationStatus(reservationStatusRepository.findByReservationStatusName(ReservationStatusName.IN_COMPLETION));
        reservationRepository.save(reservation);

        var yacht = yachtRepository.findByYachtId(reservation.getYacht().getYachtId());
        if (reservation.getPickup().getDayOfYear() == reservation.getDropoff().getDayOfYear()) {
            yacht.setYachtStatus(yachtStatusRepository.findByYachtStatusName(YachtStatusName.IN_USE));
        }
        else {
            yacht.setYachtStatus(yachtStatusRepository.findByYachtStatusName(YachtStatusName.RESERVED_LONG_TERM));
        }
        yachtRepository.save(yacht);
        var yachtHistory = YachtStatusHistory.builder()
                .yachtStatusHistoryDate(LocalDateTime.now())
                .yachtStatus(yacht.getYachtStatus())
                .yacht(yacht)
                .build();
        yachtStatusHistoryRepository.save(yachtHistory);

        var reservationHistory = ReservationStatusHistory.builder()
                .reservationStatusHistoryDate(LocalDateTime.now())
                .reservationStatus(reservation.getReservationStatus())
                .reservation(reservation)
                .build();
        reservationStatusHistoryRepository.save(reservationHistory);
        return Boolean.TRUE;
    }

    public Boolean completeReservation(String reservationId) {
        var reservation = reservationRepository.findByReservationId(Long.parseLong(reservationId));
        reservation.setReservationStatus(reservationStatusRepository.findByReservationStatusName(ReservationStatusName.RES_COMPLETED));
        reservationRepository.save(reservation);

        var yacht = yachtRepository.findByYachtId(reservation.getYacht().getYachtId());
        yacht.setYachtStatus(yachtStatusRepository.findByYachtStatusName(YachtStatusName.AVAILABLE));
        yachtRepository.save(yacht);
        var yachtHistory = YachtStatusHistory.builder()
                .yachtStatusHistoryDate(LocalDateTime.now())
                .yachtStatus(yacht.getYachtStatus())
                .yacht(yacht)
                .build();
        yachtStatusHistoryRepository.save(yachtHistory);

        var reservationHistory = ReservationStatusHistory.builder()
                .reservationStatusHistoryDate(LocalDateTime.now())
                .reservationStatus(reservation.getReservationStatus())
                .reservation(reservation)
                .build();
        reservationStatusHistoryRepository.save(reservationHistory);
        return Boolean.TRUE;
    }

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

    public Boolean reportReservationNotice(ReportReservationNoticeRequest request) {
        var reservation = reservationRepository.findByReservationId(request.getReservationId());
        var notice = Notice.builder()
                .reportedAt(LocalDateTime.now())
                .description(request.getDescription())
                .reservation(reservation)
                .noticeStatus(noticeStatusRepository.findByNoticeStatusName(NoticeStatusName.NEW))
                .build();
        noticeRepository.save(notice);
        return  Boolean.TRUE;
    }

    public ReservationNoticeResponse getReservationNotice(String reservationId) {
        var reservation = reservationRepository.findByReservationId(Long.parseLong(reservationId));
        var noticeOpt = noticeRepository.findByReservation(reservation);
        if (noticeOpt.isPresent()) {
            Notice notice = noticeOpt.get();
            var currentStatus = DictionaryResponse.builder()
                    .id(notice.getNoticeStatus().getNoticeStatusId())
                    .name(notice.getNoticeStatus().getNoticeStatusName().toString())
                    .build();
            return ReservationNoticeResponse.builder()
                    .id(notice.getNoticeId())
                    .reportedAt(notice.getReportedAt())
                    .currentStatus(currentStatus)
                    .build();
        }
        else {
            return null;
        }
    }
}
