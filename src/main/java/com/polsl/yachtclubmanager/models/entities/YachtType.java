package com.polsl.yachtclubmanager.models.entities;

import com.polsl.yachtclubmanager.enums.YachtStatusName;
import com.polsl.yachtclubmanager.enums.YachtTypeName;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "yacht_types")
public class YachtType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "yacht_type_id", nullable = false, updatable = false)
    private Long yachtTypeId;

    @Column(name = "yacht_type_name", nullable = false)
    private YachtTypeName yachtTypeName;
}
