package com.polsl.yachtclubmanager.repositories;

import com.polsl.yachtclubmanager.models.entities.Reservation;
import com.polsl.yachtclubmanager.models.entities.Yacht;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Reservation findByReservationId(Long id);
    List<Reservation> findAllByYacht(Yacht yacht);
}
