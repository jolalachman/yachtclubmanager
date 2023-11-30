package com.polsl.yachtclubmanager.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "reset_password_verifications")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class ResetPasswordVerification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reset_password_id", nullable = false)
    private Long resetPasswordId;

    @Column(name = "reset_token", unique = true)
    private String resetToken = null;

    @Column(name = "expiration_date", nullable = false)
    private Date expirationDate;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public ResetPasswordVerification(User user) {
        this.user = user;
        this.resetToken = UUID.randomUUID().toString();
        this.expirationDate = new Date(System.currentTimeMillis() + 1000 * 60 * 24);
    }
}