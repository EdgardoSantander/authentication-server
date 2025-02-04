package com.ganzo.delivery.authentication_server.starup;

import com.ganzo.delivery.authentication_server.dto.auth.RequestAuthentication;
import com.ganzo.delivery.authentication_server.dto.auth.ResponseAuthentication;
import com.ganzo.delivery.authentication_server.entity.Enrollment;
import com.ganzo.delivery.authentication_server.entity.Role;
import com.ganzo.delivery.authentication_server.entity.User;
import com.ganzo.delivery.authentication_server.repository.EnrollmentRepository;
import com.ganzo.delivery.authentication_server.repository.RolRepository;
import com.ganzo.delivery.authentication_server.services.security.AuthAndCreateUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class StartUp implements ApplicationRunner {

    private final Logger logger = LogManager.getLogger(StartUp.class);

    @Autowired
    private AuthAndCreateUserService authAndCreateUserService;

    @Autowired
    private RolRepository roleRepository;
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Override
    public void run(ApplicationArguments args) {
        try {
            Role role = Role.builder()
                    .title("ADMIN")
                    .description("administrador")
                    .build();

            role = roleRepository.save(role);


            User user = User.builder()
                    .email("startup@gmail.com")
                    .password("qwerty123")
                    .build();

            user = authAndCreateUserService.createAccount(user);

            Enrollment enrollment = Enrollment.builder()
                    .user(user)
                    .role(role)
                    .build();

            enrollment = enrollmentRepository.save(enrollment);

            logger.info("ToString user: {}", enrollment.toString());

            user.setPassword("qwerty123");

            ResponseAuthentication responseAuthentication = authAndCreateUserService.authentication(
                    RequestAuthentication.builder()
                            .email(user.getEmail())
                            .password(user.getPassword())
                            .build());

            logger.info("Token generate: {}", responseAuthentication.toString());
        } catch (Exception e) {
            logger.error("error: {}", e.getMessage());
        }
    }

}
