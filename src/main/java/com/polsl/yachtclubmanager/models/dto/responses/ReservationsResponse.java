package com.polsl.yachtclubmanager.models.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationsResponse {
    private Long id;
    private LocalDateTime pickupDate;
    private LocalDateTime dropoffDate;

    private Long yachtId;
    private String yachtName;
    private String reservingPerson;
    private String clientInfo;
    private String currentStatus;
    private String photo;
}
