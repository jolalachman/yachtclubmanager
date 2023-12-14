package com.polsl.yachtclubmanager.services;

import com.polsl.yachtclubmanager.enums.RoleName;
import com.polsl.yachtclubmanager.enums.YachtStatusName;
import com.polsl.yachtclubmanager.models.dto.other.PageInfo;
import com.polsl.yachtclubmanager.models.dto.requests.YachtRequest;
import com.polsl.yachtclubmanager.models.dto.responses.UserResponse;
import com.polsl.yachtclubmanager.models.dto.responses.YachtsListResponse;
import com.polsl.yachtclubmanager.models.dto.responses.YachtsResponse;
import com.polsl.yachtclubmanager.models.entities.*;
import com.polsl.yachtclubmanager.repositories.EquipmentRepository;
import com.polsl.yachtclubmanager.repositories.TechnicalDataRepository;
import com.polsl.yachtclubmanager.repositories.YachtRepository;
import com.polsl.yachtclubmanager.repositories.YachtStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class YachtService {
    private final YachtRepository yachtRepository;
    private final TechnicalDataRepository technicalDataRepository;
    private final EquipmentRepository equipmentRepository;
    private final YachtStatusRepository yachtStatusRepository;

    public Boolean addYacht(YachtRequest yachtRequest) {
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
                .type(yachtRequest.getType())
                .registrationNumber(yachtRequest.getRegistrationNumber())
                .yachtStatus(yachtStatusRepository.findByYachtStatusName(YachtStatusName.AVAILABLE))
                .description(yachtRequest.getDescription())
                .photo(yachtRequest.getPhoto())
                .dailyPrice(yachtRequest.getDailyPrice())
                .hourlyPrice(yachtRequest.getHourlyPrice())
                .build();
        yachtRepository.save(yacht);

        TechnicalData technicalData = new TechnicalData(yacht, technicalDataTmp);
        technicalDataRepository.save(technicalData);

        Equipment equipment = new Equipment(yacht, equipmentTmp);
        equipmentRepository.save(equipment);

        return Boolean.TRUE;
    }
    public YachtsListResponse getYachts() {
        var yachts = yachtRepository.findAll();
        var items = yachts.stream()
                    .map(this::mapToYachtResponse)
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

    private YachtsResponse mapToYachtResponse(Yacht yacht) {
        return YachtsResponse.builder()
                .id(yacht.getYachtId())
                .name(yacht.getName())
                .type(yacht.getType())
                .registrationNumber(yacht.getRegistrationNumber())
                .currentStatus(yacht.getYachtStatus().getYachtStatusName().toString())
                .photo(yacht.getPhoto())
                .build();
    }
}
