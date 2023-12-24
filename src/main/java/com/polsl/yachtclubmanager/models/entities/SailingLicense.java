package com.polsl.yachtclubmanager.models.entities;

import com.polsl.yachtclubmanager.enums.RoleName;
import com.polsl.yachtclubmanager.enums.SailingLicenseName;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sailing_licenses")
public class SailingLicense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sailing_license_id", nullable = false, updatable = false)
    private Long sailingLicenseId;

    @Column(name = "sailing_license_name", nullable = false)
    private SailingLicenseName sailingLicenseName;
}
