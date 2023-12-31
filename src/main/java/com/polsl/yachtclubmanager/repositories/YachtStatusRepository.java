package com.polsl.yachtclubmanager.repositories;

import com.polsl.yachtclubmanager.enums.YachtStatusName;
import com.polsl.yachtclubmanager.models.entities.YachtStatus;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YachtStatusRepository extends JpaRepository<YachtStatus, Long> {
    @NotNull(message="Yacht status must be provided") YachtStatus findByYachtStatusName(YachtStatusName yachtStatusName);
    @NotNull(message="Yacht status must be provided") YachtStatus findByYachtStatusId(Long yachtStatusId);
}
