package com.polsl.yachtclubmanager.repositories;

import com.polsl.yachtclubmanager.enums.YachtStatusName;
import com.polsl.yachtclubmanager.enums.YachtTypeName;
import com.polsl.yachtclubmanager.models.entities.YachtStatus;
import com.polsl.yachtclubmanager.models.entities.YachtType;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YachtTypeRepository extends JpaRepository<YachtType, Long> {
    @NotNull(message="Yacht type must be provided") YachtType findByYachtTypeName(YachtTypeName yachtTypeName);
    @NotNull(message="Yacht type must be provided") YachtType findByYachtTypeId(Long yachtTypeId);
}
