package com.polsl.yachtclubmanager.data_loader;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.polsl.yachtclubmanager.enums.RoleName;
import com.polsl.yachtclubmanager.models.entities.User;
import com.polsl.yachtclubmanager.repositories.RoleRepository;
import com.polsl.yachtclubmanager.repositories.SailingLicenseRepository;
import com.polsl.yachtclubmanager.repositories.UserRepository;

@Component
public class InitialDataLoader implements ApplicationListener<ApplicationReadyEvent> {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final SailingLicenseRepository sailingLicenseRepository;
    private final PasswordEncoder passwordEncoder;

    public InitialDataLoader(UserRepository userRepository, RoleRepository roleRepository,
                             SailingLicenseRepository sailingLicenseRepository,
                             PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.sailingLicenseRepository = sailingLicenseRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (userRepository.count() == 0) {
            loadInitialUserData();
        }
    }

    private void loadInitialUserData() {
        // Create your initial user
        var admin = User.builder()
                .firstName("Admin")
                .lastName("-")
                .email("yachtclubpolsl@gmail.com")
                .password(passwordEncoder.encode("P@ssw0rd"))
                .role(roleRepository.findByRoleName(RoleName.ADMIN))
                .sailingLicense(sailingLicenseRepository.findBySailingLicenseId(1L))
                .enabled(true)
                .nonLocked(true)
                .build();
        userRepository.save(admin);
    }
}
