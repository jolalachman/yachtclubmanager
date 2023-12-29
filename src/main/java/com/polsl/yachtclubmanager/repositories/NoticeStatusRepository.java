package com.polsl.yachtclubmanager.repositories;

import com.polsl.yachtclubmanager.enums.NoticeStatusName;
import com.polsl.yachtclubmanager.enums.ReservationStatusName;
import com.polsl.yachtclubmanager.models.entities.NoticeStatus;
import com.polsl.yachtclubmanager.models.entities.ReservationStatus;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeStatusRepository extends JpaRepository<NoticeStatus, Long> {
    @NotNull(message="Notice status must be provided") NoticeStatus findByNoticeStatusName(NoticeStatusName noticeStatusName);
    @NotNull(message="Notice status must be provided") NoticeStatus findByNoticeStatusId(Long noticeStatusId);
}
