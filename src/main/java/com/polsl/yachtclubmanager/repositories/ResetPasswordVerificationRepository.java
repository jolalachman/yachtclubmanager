package com.polsl.yachtclubmanager.repositories;

import com.polsl.yachtclubmanager.models.entities.ResetPasswordVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResetPasswordVerificationRepository extends JpaRepository<ResetPasswordVerification, Long> {
    Optional<ResetPasswordVerification> findByResetToken(String resetToken);
}
