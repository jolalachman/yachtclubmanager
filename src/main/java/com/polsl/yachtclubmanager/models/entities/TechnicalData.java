package com.polsl.yachtclubmanager.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "technical_data")
public class TechnicalData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "technical_data_id", nullable = false, updatable = false)
    private Long technicalDataId;

    @Column(name = "length")
    private Float length;

    @Column(name = "width")
    private Float width;

    @Column(name = "immersion")
    private Float immersion;

    @Column(name = "sail_area")
    private Float sailArea;

    @Column(name = "max_people")
    private Integer maxPeople;

    @Column(name = "cabin_number")
    private Integer cabinNumber;

    @OneToOne(targetEntity = Yacht.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "yacht_id", nullable = false)
    private Yacht yacht;

    public TechnicalData(Yacht yacht, TechnicalData technicalData) {
        this.yacht = yacht;
        this.length = technicalData.getLength();
        this.width = technicalData.getWidth();
        this.immersion = technicalData.getImmersion();
        this.sailArea = technicalData.getSailArea();
        this.maxPeople = technicalData.getMaxPeople();
        this.cabinNumber = technicalData.getCabinNumber();
    }
}
