package com.polsl.yachtclubmanager.repositories;

import com.polsl.yachtclubmanager.enums.RoleName;
import com.polsl.yachtclubmanager.enums.SailingLicenseName;
import com.polsl.yachtclubmanager.models.entities.Role;
import com.polsl.yachtclubmanager.models.entities.SailingLicense;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SailingLicenseRepository extends JpaRepository<SailingLicense, Long> {
    @NotNull(message = "Sailing license must be provided") SailingLicense findBySailingLicenseId(Long sailingLicenseId);
}
