package com.polsl.yachtclubmanager.services;

import com.polsl.yachtclubmanager.enums.ReservationStatusName;
import com.polsl.yachtclubmanager.enums.YachtStatusName;
import com.polsl.yachtclubmanager.enums.YachtTypeName;
import com.polsl.yachtclubmanager.models.dto.other.PageInfo;
import com.polsl.yachtclubmanager.models.dto.requests.ChangeStatusRequest;
import com.polsl.yachtclubmanager.models.dto.requests.EditYachtRequest;
import com.polsl.yachtclubmanager.models.dto.requests.YachtRequest;
import com.polsl.yachtclubmanager.models.dto.responses.*;
import com.polsl.yachtclubmanager.models.entities.*;
import com.polsl.yachtclubmanager.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class YachtService {
    private final YachtRepository yachtRepository;
    private final TechnicalDataRepository technicalDataRepository;
    private final EquipmentRepository equipmentRepository;
    private final YachtStatusRepository yachtStatusRepository;
    private final ReservationRepository reservationRepository;
    private final YachtTypeRepository yachtTypeRepository;
    private final ReservationStatusRepository reservationStatusRepository;
    private final EmailService emailService;
    private final YachtStatusHistoryRepository yachtStatusHistoryRepository;

    public Boolean addYacht(YachtRequest yachtRequest) {
        var yachtCheck = yachtRepository.findByRegistrationNumber(yachtRequest.getRegistrationNumber());
        if (yachtCheck.isPresent()) {
            return Boolean.FALSE;
        }
        var technicalDataTmp = TechnicalData.builder()
                .length(yachtRequest.getLength())
                .width(yachtRequest.getWidth())
                .immersion(yachtRequest.getImmersion())
                .sailArea(yachtRequest.getSailArea())
                .maxPeople(yachtRequest.getMaxPeople())
                .cabinNumber(yachtRequest.getCabinNumber())
                .build();

        var equipmentTmp = Equipment.builder()
                .shower(yachtRequest.getShower())
                .wc(yachtRequest.getWc())
                .microwave(yachtRequest.getMicrowave())
                .radio(yachtRequest.getRadio())
                .build();

        var yacht = Yacht.builder()
                .name(yachtRequest.getName())
                .yachtType(yachtTypeRepository.findByYachtTypeId(yachtRequest.getType()))
                .registrationNumber(yachtRequest.getRegistrationNumber())
                .yachtStatus(yachtStatusRepository.findByYachtStatusName(YachtStatusName.AVAILABLE))
                .description(yachtRequest.getDescription())
                .photo(yachtRequest.getPhoto())
                .dailyPrice(yachtRequest.getDailyPrice())
                .hourlyPrice(yachtRequest.getHourlyPrice())
                .build();
        yachtRepository.save(yacht);

        var yachtHistory = YachtStatusHistory.builder()
                .yachtStatusHistoryDate(LocalDateTime.now())
                .yachtStatus(yacht.getYachtStatus())
                .yacht(yacht)
                .build();

        yachtStatusHistoryRepository.save(yachtHistory);

        TechnicalData technicalData = new TechnicalData(yacht, technicalDataTmp);
        technicalDataRepository.save(technicalData);

        Equipment equipment = new Equipment(yacht, equipmentTmp);
        equipmentRepository.save(equipment);

        return Boolean.TRUE;
    }
    public Boolean editYacht(EditYachtRequest editYachtRequest) {
        var yacht = yachtRepository.findByYachtId(Long.parseLong(editYachtRequest.getId()));
        var equipment = equipmentRepository.findByYacht(yacht);
        var technicalData = technicalDataRepository.findByYacht(yacht);

        equipment.setShower(editYachtRequest.getShower());
        equipment.setWc(editYachtRequest.getWc());
        equipment.setMicrowave(editYachtRequest.getMicrowave());
        equipment.setRadio(editYachtRequest.getRadio());
        equipmentRepository.save(equipment);

        technicalData.setLength(editYachtRequest.getLength());
        technicalData.setWidth(editYachtRequest.getWidth());
        technicalData.setImmersion(editYachtRequest.getImmersion());
        technicalData.setSailArea(editYachtRequest.getSailArea());
        technicalData.setMaxPeople(editYachtRequest.getMaxPeople());
        technicalData.setCabinNumber(editYachtRequest.getCabinNumber());
        technicalDataRepository.save(technicalData);

        yacht.setName(editYachtRequest.getName());
        yacht.setYachtType(yachtTypeRepository.findByYachtTypeId(Long.parseLong(editYachtRequest.getType())));
        yacht.setRegistrationNumber(editYachtRequest.getRegistrationNumber());
        yacht.setDescription(editYachtRequest.getDescription());
        yacht.setPhoto(editYachtRequest.getPhoto());
        yacht.setDailyPrice(editYachtRequest.getDailyPrice());
        yacht.setHourlyPrice(editYachtRequest.getHourlyPrice());
        yachtRepository.save(yacht);

        return Boolean.TRUE;
    }
    public Boolean deactivateYacht(Long yachtId) {
        var yacht = yachtRepository.findByYachtId(yachtId);
        yacht.setYachtStatus(yachtStatusRepository.findByYachtStatusName(YachtStatusName.DEACTIVATED));
        var yachtHistory = YachtStatusHistory.builder()
                .yachtStatusHistoryDate(LocalDateTime.now())
                .yachtStatus(yacht.getYachtStatus())
                .yacht(yacht)
                .build();

        yachtStatusHistoryRepository.save(yachtHistory);
        var reservations = reservationRepository.findAllByYacht(yacht);
        var items = reservations.stream()
                .filter(reservation -> reservation.getReservationStatus().getReservationStatusName().equals(ReservationStatusName.PENDING) ||
                        reservation.getReservationStatus().getReservationStatusName().equals(ReservationStatusName.CONFIRMED))
                .map(this::setCancelledStatus)
                .toList();

        yachtRepository.save(yacht);
        return Boolean.TRUE;
    }
    public YachtsListResponse getYachts() {
        var yachts = yachtRepository.findAll();
        var items = yachts.stream()
                    .filter(yacht -> !yacht.getYachtStatus().getYachtStatusName().equals(YachtStatusName.DEACTIVATED))
                    .map(yacht -> mapToYachtResponse(yacht, technicalDataRepository.findByYacht(yacht)))
                    .collect(Collectors.toList());
        var pageInfo = PageInfo.builder()
                       .offset(0L)
                       .limit(10L)
                       .build();
        var totalCount = items.size();
        return YachtsListResponse.builder()
                .items(items)
                .pageInfo(pageInfo)
                .totalCount(totalCount)
                .build();
    }
    public YachtResponse getYacht(Long yachtId) {
        var yacht = yachtRepository.findByYachtId(yachtId);
        var technicalData = technicalDataRepository.findByYacht(yacht);
        var equipment = equipmentRepository.findByYacht(yacht);
        var reservations = reservationRepository.findAllByYacht(yacht);
        var items = reservations.stream()
                .filter(reservation -> !reservation.getReservationStatus().getReservationStatusName().equals(ReservationStatusName.CANCELLED))
                .map(reservation -> mapToReservationResponse(reservation))
                .collect(Collectors.toList());
        var yachtType = DictionaryResponse.builder()
                .id(yacht.getYachtType().getYachtTypeId())
                .name(yacht.getYachtType().getYachtTypeName().toString())
                .build();
        var currentStatus = DictionaryResponse.builder()
                .id(yacht.getYachtStatus().getYachtStatusId())
                .name(yacht.getYachtStatus().getYachtStatusName().toString())
                .build();
        return YachtResponse.builder()
                .id(yacht.getYachtId())
                .name(yacht.getName())
                .type(yachtType)
                .registrationNumber(yacht.getRegistrationNumber())
                .currentStatus(currentStatus)
                .photo(yacht.getPhoto())
                .description(yacht.getDescription())
                .technicalData(technicalData)
                .equipment(equipment)
                .reservations(items)
                .dailyPrice(yacht.getDailyPrice())
                .hourlyPrice(yacht.getHourlyPrice())
                .build();
    }
    private ReservationResponse mapToReservationResponse(Reservation reservation) {
        return ReservationResponse.builder()
                .id(reservation.getReservationId())
                .pickupDate(reservation.getPickup())
                .dropoffDate(reservation.getDropoff())
                .clientInfo(reservation.getUser().getFirstName() + " " + reservation.getUser().getLastName())
                .currentStatus(reservation.getReservationStatus().getReservationStatusName().toString())
                .build();
    }
    private YachtsResponse mapToYachtResponse(Yacht yacht, TechnicalData technicalData) {
        var yachtType = DictionaryResponse.builder()
                .id(yacht.getYachtType().getYachtTypeId())
                .name(yacht.getYachtType().getYachtTypeName().toString())
                .build();
        var currentStatus = DictionaryResponse.builder()
                .id(yacht.getYachtStatus().getYachtStatusId())
                .name(yacht.getYachtStatus().getYachtStatusName().toString())
                .build();
        return YachtsResponse.builder()
                .id(yacht.getYachtId())
                .name(yacht.getName())
                .type(yachtType)
                .registrationNumber(yacht.getRegistrationNumber())
                .currentStatus(currentStatus)
                .photo(yacht.getPhoto())
                .cabinNum(technicalData.getCabinNumber())
                .peopleNum(technicalData.getMaxPeople())
                .reservations(reservationRepository.findAllByYacht(yacht))
                .build();
    }
    public Boolean changeYachtStatus(ChangeStatusRequest changeStatusRequest) {
        var yacht = yachtRepository.findByYachtId(changeStatusRequest.getId());
        yacht.setYachtStatus(yachtStatusRepository.findByYachtStatusId(Long.parseLong(changeStatusRequest.getStatus())));
        yachtRepository.save(yacht);
        var yachtHistory = YachtStatusHistory.builder()
                .yachtStatusHistoryDate(LocalDateTime.now())
                .yachtStatus(yacht.getYachtStatus())
                .yacht(yacht)
                .build();

        yachtStatusHistoryRepository.save(yachtHistory);
        return Boolean.TRUE;
    }

    private Reservation setCancelledStatus(Reservation reservation) {
        reservation.setReservationStatus(reservationStatusRepository.findByReservationStatusName(ReservationStatusName.CANCELLED));
        reservationRepository.save(reservation);
        emailService.sendReservationCancelled(reservation.getUser().getEmail(), reservation.getYacht().getName(), '#'+reservation.getReservationId().toString());
        return reservation;
    }

    public List<YachtStatusHistoryResponse> getYachtStatusHistory(Long yachtId) {
        var yacht = yachtRepository.findByYachtId(yachtId);
        var yachtStatusHistory = yachtStatusHistoryRepository.findAllByYacht(yacht);
        var items = yachtStatusHistory.stream()
                .map(history -> mapToStatusHistoryResponse(history))
                .collect(Collectors.toList());
        return items;
    }

    private YachtStatusHistoryResponse mapToStatusHistoryResponse(YachtStatusHistory yachtStatusHistory) {
        return YachtStatusHistoryResponse.builder()
                .statusDate(yachtStatusHistory.getYachtStatusHistoryDate())
                .statusName(yachtStatusHistory.getYachtStatus().getYachtStatusName().toString())
                .build();
    }

}
