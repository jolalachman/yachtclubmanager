package com.polsl.yachtclubmanager.models.dto.responses;

import com.polsl.yachtclubmanager.models.dto.other.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoticesListResponse {
    private List<NoticesResponse> items;
    private PageInfo pageInfo;
    private Integer totalCount;
}
