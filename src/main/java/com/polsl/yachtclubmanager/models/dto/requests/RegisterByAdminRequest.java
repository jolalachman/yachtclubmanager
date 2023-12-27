package com.polsl.yachtclubmanager.models.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterByAdminRequest {

    private String firstName;
    private String lastName;
    private String role;
    private String clubStatus;
    private Long sailingLicense;
    private String email;
    private String password;

}
