package com.polsl.yachtclubmanager.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Table(name = "reset_password_verifications")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class ResetPasswordVerification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "reset_password_id", nullable = false)
    private Long resetPasswordId;

    @Column(name = "url", unique = true)
    private String url = null;

    @Column(name = "expiration_date", nullable = false)
    private LocalDateTime expirationDate;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "user_id")
    @NotNull(message = "User must be provided")
    @JsonBackReference
    private User user;
}