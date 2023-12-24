package com.polsl.yachtclubmanager.repositories;

import com.polsl.yachtclubmanager.enums.ReservationStatusName;
import com.polsl.yachtclubmanager.enums.YachtStatusName;
import com.polsl.yachtclubmanager.models.entities.ReservationStatus;
import com.polsl.yachtclubmanager.models.entities.YachtStatus;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationStatusRepository extends JpaRepository<ReservationStatus, Long> {
    @NotNull(message="Reservation status must be provided") ReservationStatus findByReservationStatusName(ReservationStatusName reservationStatusName);
}
