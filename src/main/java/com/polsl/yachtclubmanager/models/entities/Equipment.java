package com.polsl.yachtclubmanager.models.entities;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "equipment")
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "equipment_id", nullable = false, updatable = false)
    private Long equipmentId;

    @Column(name = "shower")
    private Boolean shower;

    @Column(name = "wc")
    private Boolean wc;

    @Column(name = "microwave")
    private Boolean microwave;

    @Column(name = "radio")
    private Boolean radio;

    @OneToOne(targetEntity = Yacht.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "yacht_id", nullable = false)
    private Yacht yacht;

    public Equipment(Yacht yacht, Equipment equipment) {
        this.yacht = yacht;
        this.shower = equipment.getShower();
        this.wc = equipment.getWc();
        this.microwave = equipment.getMicrowave();
        this.radio = equipment.getRadio();
    }
}
