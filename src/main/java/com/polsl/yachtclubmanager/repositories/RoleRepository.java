package com.polsl.yachtclubmanager.repositories;

import com.polsl.yachtclubmanager.enums.RoleName;
import com.polsl.yachtclubmanager.models.entities.Role;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @NotNull(message = "Role must be provided") Role findByRoleName(RoleName roleName);
}
