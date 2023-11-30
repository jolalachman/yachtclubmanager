package com.polsl.yachtclubmanager.repositories;

import com.polsl.yachtclubmanager.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserId(Long id);

    Optional<User> findByEmail(String email);

    User findByEmailIgnoreCase(String email);
}
