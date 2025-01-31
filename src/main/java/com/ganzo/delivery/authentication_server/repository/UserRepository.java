package com.ganzo.delivery.authentication_server.repository;

import com.ganzo.delivery.authentication_server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository< User, Long > {

    Optional<User> findByEmail(String email);
}
