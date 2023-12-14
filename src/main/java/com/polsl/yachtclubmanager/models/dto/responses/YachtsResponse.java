package com.polsl.yachtclubmanager.models.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
