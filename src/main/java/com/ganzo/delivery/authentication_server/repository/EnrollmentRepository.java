package com.ganzo.delivery.authentication_server.repository;

import com.ganzo.delivery.authentication_server.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment,Long> {
}
