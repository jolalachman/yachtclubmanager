package com.polsl.yachtclubmanager.repositories;

import com.polsl.yachtclubmanager.models.entities.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationStatusHistoryRepository extends JpaRepository<ReservationStatusHistory, Long> {
    @NotNull(message="Reservation status history must be provided") ReservationStatusHistory findByReservationStatus(ReservationStatus reservationStatus);
    @NotNull(message="Reservation status history must be provided") List<ReservationStatusHistory> findAllByReservation(Reservation reservation);
    @NotNull(message="Reservation status history must be provided") ReservationStatusHistory findByReservationStatusHistoryId(Long reservationStatusHistoryId);
}
