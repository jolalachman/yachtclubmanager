package com.polsl.yachtclubmanager.models.dto.responses;

import com.polsl.yachtclubmanager.models.entities.Equipment;
import com.polsl.yachtclubmanager.models.entities.TechnicalData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class YachtResponse {
    private Long id;
    private String name;
    private String type;
    private String registrationNumber;
    private String currentStatus;
    private String photo;
    private String description;
    private TechnicalData technicalData;
    private Equipment equipment;
    private List<ReservationResponse> reservations;
    private Float dailyPrice;
    private Float hourlyPrice;
}
