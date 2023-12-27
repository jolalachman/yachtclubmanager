package com.polsl.yachtclubmanager.models.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EditYachtRequest {
    private String id;
    private String name;
    private String type;
    private String registrationNumber;
    private String description;
    private String photo;
    private Float length;
    private Float width;
    private Float immersion;
    private Float sailArea;
    private Integer maxPeople;
    private Integer cabinNumber;
    private Boolean shower;
    private Boolean wc;
    private Boolean microwave;
    private Boolean radio;
    private Float dailyPrice;
    private Float hourlyPrice;
}
