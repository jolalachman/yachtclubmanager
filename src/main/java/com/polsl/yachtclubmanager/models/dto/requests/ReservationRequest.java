package com.polsl.yachtclubmanager.models.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequest {
    private String pickup;
    private String dropoff;

    private Integer peopleNumber;
    private String reservingPersonId;
    private Long userId;
    private Long yachtId;
}
