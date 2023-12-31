package com.polsl.yachtclubmanager.repositories;

import com.polsl.yachtclubmanager.enums.YachtStatusName;
import com.polsl.yachtclubmanager.models.entities.Yacht;
import com.polsl.yachtclubmanager.models.entities.YachtStatus;
import com.polsl.yachtclubmanager.models.entities.YachtStatusHistory;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface YachtStatusHistoryRepository extends JpaRepository<YachtStatusHistory, Long> {
    @NotNull(message="Yacht status history must be provided") YachtStatusHistory findByYachtStatus(YachtStatus yachtStatus);
    @NotNull(message="Yacht status history must be provided") List<YachtStatusHistory> findAllByYacht(Yacht yacht);
    @NotNull(message="Yacht status history must be provided") YachtStatusHistory findByYachtStatusHistoryId(Long yachtStatusHistoryId);
}
