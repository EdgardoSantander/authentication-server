package com.ganzo.delivery.authentication_server.repository;

import com.ganzo.delivery.authentication_server.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<Role, Long> {
}
