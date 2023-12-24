package com.polsl.yachtclubmanager.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id", nullable = false, updatable = false)
    private Long reservationId;

    @NotNull(message = "Pick up datetime must not be blank")
    @Column(name = "pickup", nullable = false)
    private LocalDateTime pickup;

    @NotNull(message = "Drop off datetime must not be blank")
    @Column(name = "dropoff", nullable = false)
    private LocalDateTime dropoff;
    @Column(name = "reservingPerson", nullable = false)
    private String reservingPerson;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "yacht_id")
    @NotNull(message = "Yacht must be provided")
    @JsonBackReference
    private Yacht yacht;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "user_id")
    @NotNull(message = "User must be provided")
    @JsonBackReference
    private User user;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "reservation_status_id")
    @NotNull(message = "Reservation status must be provided")
    @JsonBackReference
    private ReservationStatus reservationStatus;
}
