package com.polsl.yachtclubmanager.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.polsl.yachtclubmanager.enums.YachtStatusName;
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
@Table(name = "yacht_status_history")
public class YachtStatusHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "yacht_status_history_id", nullable = false, updatable = false)
    private Long yachtStatusHistoryId;

    @Column(name = "yacht_status_history_date", nullable = false)
    private LocalDateTime yachtStatusHistoryDate;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "yacht_status_id")
    @NotNull(message = "Yacht status must be provided")
    @JsonBackReference
    private YachtStatus yachtStatus;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "yacht_id")
    @NotNull(message = "Yacht must be provided")
    @JsonBackReference
    private Yacht yacht;
}
