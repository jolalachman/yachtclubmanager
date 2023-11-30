package com.polsl.yachtclubmanager.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Table(name = "account_verifications")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class AccountVerification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_verification_id", nullable = false)
    private Long accountVerificationId;

    @Column(name = "token", unique = true)
    private String token = null;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public AccountVerification(User user) {
        this.user = user;
        this.token = UUID.randomUUID().toString();
    }

}