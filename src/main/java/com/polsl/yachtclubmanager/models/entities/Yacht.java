package com.polsl.yachtclubmanager.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "yachts")
public class Yacht {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "yacht_id", nullable = false, updatable = false)
    private Long yachtId;

    @NotBlank(message = "Name must not be blank")
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type")
    private String type;

    @NotBlank(message = "Registration number must not be blank")
    @Column(name = "registration_number", nullable = false)
    private String registrationNumber;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Lob
    @Column(name = "photo", columnDefinition = "TEXT")
    private String photo;

    @Column(name = "dailyPrice")
    private Float dailyPrice;

    @Column(name = "hourlyPrice")
    private Float hourlyPrice;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "yacht_status_id")
    @NotNull(message = "Yacht status must be provided")
    @JsonBackReference
    private YachtStatus yachtStatus;
}
