package com.polsl.yachtclubmanager.repositories;

import com.polsl.yachtclubmanager.enums.RoleName;
import com.polsl.yachtclubmanager.models.entities.Role;
import com.polsl.yachtclubmanager.models.entities.TechnicalData;
import com.polsl.yachtclubmanager.models.entities.Yacht;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechnicalDataRepository extends JpaRepository<TechnicalData, Long> {
    @NotNull(message = "Technical data must be provided") TechnicalData findByYacht(Yacht yacht);
}
