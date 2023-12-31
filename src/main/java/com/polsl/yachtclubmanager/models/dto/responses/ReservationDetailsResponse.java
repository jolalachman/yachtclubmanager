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
public class ReservationDetailsResponse {
    private Long id;
    private LocalDateTime pickupDate;
    private LocalDateTime dropoffDate;
    private Integer peopleNumber;
    private DictionaryResponse yacht;
    private DictionaryResponse reservingPerson;
    private DictionaryResponse clientInfo;
    private DictionaryResponse currentStatus;
    private String photo;

    private Boolean canReportNotice;
}
