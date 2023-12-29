package com.polsl.yachtclubmanager.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
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
@Table(name = "notices")
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id", nullable = false, updatable = false)
    private Long noticeId;

    @NotNull(message = "Reported datetime must not be blank")
    @Column(name = "reported-at", nullable = false)
    private LocalDateTime reportedAt;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "reservation_id")
    @NotNull(message = "Reservation must be provided")
    @JsonBackReference
    private Reservation reservation;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "notice_status_id")
    @NotNull(message = "Notice status must be provided")
    @JsonBackReference
    private NoticeStatus noticeStatus;
}
