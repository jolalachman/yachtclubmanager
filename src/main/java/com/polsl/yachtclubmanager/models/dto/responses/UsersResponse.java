package com.polsl.yachtclubmanager.models.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsersResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String roleName;
    private String clubStatus;
    private DictionaryResponse sailingLicense;
}
