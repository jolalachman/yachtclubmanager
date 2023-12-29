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
    @Column(name = "name", nullable = false, columnDefinition = "VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String name;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "yacht_type_id")
    @NotNull(message = "Yacht type must be provided")
    @JsonBackReference
    private YachtType yachtType;

    @NotBlank(message = "Registration number must not be blank")
    @Column(name = "registration_number", nullable = false, unique = true, columnDefinition = "VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String registrationNumber;

    @Column(name = "description", columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String description;

    @Lob
    @Column(name = "photo", columnDefinition = "TEXT")
    private String photo;

    @Column(name = "daily_price")
    private Float dailyPrice;

    @Column(name = "hourly_price")
    private Float hourlyPrice;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "yacht_status_id")
    @NotNull(message = "Yacht status must be provided")
    @JsonBackReference
    private YachtStatus yachtStatus;
}
