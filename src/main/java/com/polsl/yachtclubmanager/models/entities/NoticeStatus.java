package com.polsl.yachtclubmanager.models.entities;

import com.polsl.yachtclubmanager.enums.NoticeStatusName;
import com.polsl.yachtclubmanager.enums.ReservationStatusName;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notice_statuses")
public class NoticeStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_status_id", nullable = false, updatable = false)
    private Long noticeStatusId;

    @Column(name = "notice_status_name", nullable = false)
    private NoticeStatusName noticeStatusName;
}
