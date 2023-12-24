package com.polsl.yachtclubmanager.models.dto.responses;

import com.polsl.yachtclubmanager.models.entities.Reservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class YachtsResponse {
    private Long id;
    private String name;
    private String type;
    private String registrationNumber;
    private String currentStatus;
    private String photo;
    private Integer cabinNum;
    private Integer peopleNum;
    private List<Reservation> reservations;
}
