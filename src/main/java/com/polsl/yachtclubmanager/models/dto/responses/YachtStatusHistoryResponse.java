package com.polsl.yachtclubmanager.models.dto.responses;

import com.polsl.yachtclubmanager.models.entities.Reservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class YachtStatusHistoryResponse {

    private LocalDateTime statusDate;
    private String statusName;
}
