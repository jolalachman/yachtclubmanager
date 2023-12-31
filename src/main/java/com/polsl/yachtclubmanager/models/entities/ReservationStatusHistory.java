package com.polsl.yachtclubmanager.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reservation_status_history")
public class ReservationStatusHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_status_history_id", nullable = false, updatable = false)
    private Long reservationStatusHistoryId;

    @Column(name = "reservation_status_history_date", nullable = false)
    private LocalDateTime reservationStatusHistoryDate;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "reservation_status_id")
    @NotNull(message = "Reservation status must be provided")
    @JsonBackReference
    private ReservationStatus reservationStatus;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "reservation_id")
    @NotNull(message = "Reservation must be provided")
    @JsonBackReference
    private Reservation reservation;
}
