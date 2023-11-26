package com.polsl.yachtclubmanager.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "account_verifications")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class AccountVerification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_verification_id", nullable = false)
    private Long accountVerificationId;

    @Column(name = "url", unique = true)
    private String url = null;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "user_id")
    @NotNull(message = "User must be provided")
    @JsonBackReference
    private User user;

}