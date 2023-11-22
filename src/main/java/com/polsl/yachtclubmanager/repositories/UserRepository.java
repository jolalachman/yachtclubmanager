package com.polsl.yachtclubmanager.repositories;

import com.polsl.yachtclubmanager.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
