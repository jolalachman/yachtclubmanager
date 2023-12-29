package com.polsl.yachtclubmanager.services;

import com.polsl.yachtclubmanager.enums.ReservationStatusName;
import com.polsl.yachtclubmanager.enums.RoleName;
import com.polsl.yachtclubmanager.enums.YachtStatusName;
import com.polsl.yachtclubmanager.models.dto.other.PageInfo;
import com.polsl.yachtclubmanager.models.dto.responses.*;
import com.polsl.yachtclubmanager.models.entities.Notice;
import com.polsl.yachtclubmanager.models.entities.Reservation;
import com.polsl.yachtclubmanager.repositories.NoticeRepository;
import com.polsl.yachtclubmanager.repositories.SailingLicenseRepository;
import com.polsl.yachtclubmanager.repositories.UserRepository;
import com.polsl.yachtclubmanager.repositories.YachtRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public NoticesListResponse getAllNotices() {
        var notices = noticeRepository.findAll();
        var items = notices.stream()
                .map(this::mapToNoticesResponse)
                .collect(Collectors.toList());
        var pageInfo = PageInfo.builder()
                .offset(0L)
                .limit(10L)
                .build();
        var totalCount = items.size();

        return NoticesListResponse.builder()
                .items(items)
                .pageInfo(pageInfo)
                .totalCount(totalCount)
                .build();
    }

    private NoticesResponse mapToNoticesResponse(Notice notice) {
        var yacht = DictionaryResponse.builder()
                .id(notice.getReservation().getYacht().getYachtId())
                .name(notice.getReservation().getYacht().getName())
                .build();
        var clubMember = DictionaryResponse.builder()
                .id(notice.getReservation().getUser().getUserId())
                .name(notice.getReservation().getUser().getFirstName() + " " + notice.getReservation().getUser().getLastName())
                .build();
        var currentStatus = DictionaryResponse.builder()
                .id(notice.getNoticeStatus().getNoticeStatusId())
                .name(notice.getNoticeStatus().getNoticeStatusName().toString())
                .build();
        return NoticesResponse.builder()
                .id(notice.getNoticeId())
                .reportedAt(notice.getReportedAt())
                .yacht(yacht)
                .reservationId(notice.getReservation().getReservationId())
                .clubMember(clubMember)
                .currentStatus(currentStatus)
                .build();
    }

}
