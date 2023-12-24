package com.polsl.yachtclubmanager.services;

import com.polsl.yachtclubmanager.enums.RoleName;
import com.polsl.yachtclubmanager.enums.YachtStatusName;
import com.polsl.yachtclubmanager.models.dto.responses.DictionaryResponse;
import com.polsl.yachtclubmanager.repositories.SailingLicenseRepository;
import com.polsl.yachtclubmanager.repositories.UserRepository;
import com.polsl.yachtclubmanager.repositories.YachtRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DictionaryService {
    private final YachtRepository yachtRepository;
    private final UserRepository userRepository;
    private final SailingLicenseRepository sailingLicenseRepository;

    public List<DictionaryResponse> getYachts() {
        var yachts = yachtRepository.findAll();
        List<DictionaryResponse> dictionaryResponses = yachts.stream()
                .filter(yacht -> !(
                        yacht.getYachtStatus().getYachtStatusName().equals(YachtStatusName.IN_REPAIR) ||
                                yacht.getYachtStatus().getYachtStatusName().equals(YachtStatusName.MALFUNCTION)
                ))
                .map(yacht -> {
                    DictionaryResponse response = new DictionaryResponse();
                    response.setId(yacht.getYachtId());
                    response.setName(yacht.getName());
                    return response;
                })
                .collect(Collectors.toList());

        return dictionaryResponses;
    }

    public List<DictionaryResponse> getUsers() {
        var users = userRepository.findAll();
        List<DictionaryResponse> dictionaryResponses = users.stream()
                .filter(user -> !(
                        user.getRole().getRoleName().equals(RoleName.ADMIN) ||
                                user.getRole().getRoleName().equals(RoleName.CANDIDATE)
                ))
                .map(user -> {
                    DictionaryResponse response = new DictionaryResponse();
                    response.setId(user.getUserId());
                    response.setName(user.getFirstName() + " " + user.getLastName());
                    return response;
                })
                .collect(Collectors.toList());

        return dictionaryResponses;
    }

    public List<DictionaryResponse> getSailingLicenses() {
        var licenses = sailingLicenseRepository.findAll();
        List<DictionaryResponse> dictionaryResponses = licenses.stream()
                .map(license -> {
                    DictionaryResponse response = new DictionaryResponse();
                    response.setId(license.getSailingLicenseId());
                    response.setName(license.getSailingLicenseName().toString());
                    return response;
                })
                .collect(Collectors.toList());

        return dictionaryResponses;
    }
}
