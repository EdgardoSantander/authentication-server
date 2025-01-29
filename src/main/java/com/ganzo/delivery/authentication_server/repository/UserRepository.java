package com.ganzo.delivery.authentication_server.repository;

import com.ganzo.delivery.authentication_server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository< User, Long > {
}
