package com.polsl.yachtclubmanager.repositories;

import com.polsl.yachtclubmanager.models.entities.Equipment;
import com.polsl.yachtclubmanager.models.entities.TechnicalData;
import com.polsl.yachtclubmanager.models.entities.Yacht;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    @NotNull(message = "Equipment must be provided") Equipment findByYacht(Yacht yacht);
}
