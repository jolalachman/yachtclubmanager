package com.polsl.yachtclubmanager.models.entities;

import com.polsl.yachtclubmanager.enums.ReservationStatusName;
import com.polsl.yachtclubmanager.enums.YachtStatusName;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reservation_statuses")
public class ReservationStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_status_id", nullable = false, updatable = false)
    private Long reservationStatusId;

    @Column(name = "reservation_status_name", nullable = false)
    private ReservationStatusName reservationStatusName;
}
