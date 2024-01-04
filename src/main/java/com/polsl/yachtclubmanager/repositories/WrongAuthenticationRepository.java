package com.polsl.yachtclubmanager.repositories;

import com.polsl.yachtclubmanager.models.entities.User;
import com.polsl.yachtclubmanager.models.entities.WrongAuthentication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WrongAuthenticationRepository extends JpaRepository<WrongAuthentication, Long> {

    List<WrongAuthentication> findAllByUser(User user);
}
