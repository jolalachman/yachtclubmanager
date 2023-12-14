package com.polsl.yachtclubmanager.repositories;

import com.polsl.yachtclubmanager.models.entities.Equipment;
import com.polsl.yachtclubmanager.models.entities.TechnicalData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
}
