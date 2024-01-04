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
@Table(name = "wrong_authentications")
public class WrongAuthentication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wrong_authentication_id", nullable = false)
    private Long wrongAuthenticationId;

    @Column(name = "wrong_authentication_date")
    private LocalDateTime wrongAuthenticationDate;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "user_id")
    @NotNull(message = "User must be provided")
    @JsonBackReference
    private User user;
}
