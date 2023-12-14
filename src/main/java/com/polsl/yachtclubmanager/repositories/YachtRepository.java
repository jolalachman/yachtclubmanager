package com.polsl.yachtclubmanager.repositories;

import com.polsl.yachtclubmanager.models.entities.Yacht;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YachtRepository extends JpaRepository<Yacht, Long> {
    Yacht findByYachtId(Long id);
}
