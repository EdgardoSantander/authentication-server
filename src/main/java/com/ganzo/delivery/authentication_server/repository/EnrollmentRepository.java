package com.ganzo.delivery.authentication_server.repository;

import com.ganzo.delivery.authentication_server.entity.Enrollment;
import com.ganzo.delivery.authentication_server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface EnrollmentRepository extends JpaRepository<Enrollment,Long> {

    Optional<Set<Enrollment>> findByUser(User user);
}
