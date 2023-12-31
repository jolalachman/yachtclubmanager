package com.polsl.yachtclubmanager.models.entities;

import com.polsl.yachtclubmanager.enums.RoleName;
import com.polsl.yachtclubmanager.enums.YachtStatusName;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "yacht_statuses")
public class YachtStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "yacht_status_id", nullable = false, updatable = false)
    private Long yachtStatusId;

    @Column(name = "yacht_status_name", nullable = false)
    private YachtStatusName yachtStatusName;
}
