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
public class NoticesResponse {
    private Long id;
    private LocalDateTime reportedAt;
    private DictionaryResponse yacht;
    private Long reservationId;
    private DictionaryResponse clubMember;
    private DictionaryResponse currentStatus;
}
