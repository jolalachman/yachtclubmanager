package com.polsl.yachtclubmanager.repositories;

import com.polsl.yachtclubmanager.models.entities.Notice;
import com.polsl.yachtclubmanager.models.entities.Reservation;
import com.polsl.yachtclubmanager.models.entities.User;
import com.polsl.yachtclubmanager.models.entities.Yacht;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    Notice findByNoticeId(Long id);
    Optional<Notice> findByReservation(Reservation reservation);
    List<Notice> findAllByReservation(Reservation reservation);
}
