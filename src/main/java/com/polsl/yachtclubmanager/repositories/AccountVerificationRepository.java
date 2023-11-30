package com.polsl.yachtclubmanager.repositories;

import com.polsl.yachtclubmanager.models.entities.AccountVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountVerificationRepository extends JpaRepository<AccountVerification, Long> {
    Optional<AccountVerification> findByToken(String token);
    List<AccountVerification> findAll();
}
