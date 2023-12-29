package com.polsl.yachtclubmanager.repositories;

import com.polsl.yachtclubmanager.models.entities.Notice;
import com.polsl.yachtclubmanager.models.entities.Reservation;
import com.polsl.yachtclubmanager.models.entities.User;
import com.polsl.yachtclubmanager.models.entities.Yacht;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    Notice findByNoticeId(Long id);
    List<Notice> findAllByReservation(Reservation reservation);
}
