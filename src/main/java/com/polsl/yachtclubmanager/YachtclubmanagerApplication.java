package com.polsl.yachtclubmanager;

import com.polsl.yachtclubmanager.enums.RoleName;
import com.polsl.yachtclubmanager.models.entities.User;
import com.polsl.yachtclubmanager.repositories.RoleRepository;
import com.polsl.yachtclubmanager.repositories.SailingLicenseRepository;
import com.polsl.yachtclubmanager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class YachtclubmanagerApplication {
	public static void main(String[] args) {
		SpringApplication.run(YachtclubmanagerApplication.class, args);
	}
}
