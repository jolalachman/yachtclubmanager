package com.polsl.yachtclubmanager.repositories;

import com.polsl.yachtclubmanager.models.entities.User;
import com.polsl.yachtclubmanager.models.entities.Yacht;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface YachtRepository extends JpaRepository<Yacht, Long> {
    Yacht findByYachtId(Long id);
    Optional<Yacht> findByRegistrationNumber(String registrationNumber);
}
