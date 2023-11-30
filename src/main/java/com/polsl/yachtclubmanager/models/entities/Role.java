package com.polsl.yachtclubmanager.models.entities;

import com.polsl.yachtclubmanager.enums.RoleName;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", nullable = false, updatable = false)
    private Long userId;

    @Column(name = "role_name", nullable = false)
    private RoleName roleName;

    @Column(name = "permission", nullable = false)
    private String permission;
}
