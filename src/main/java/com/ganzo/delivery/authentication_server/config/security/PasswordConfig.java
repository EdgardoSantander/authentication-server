package com.ganzo.delivery.authentication_server.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Configuration
public class PasswordConfig {

        @Bean
        public PasswordEncoder passwordEncoder() throws NoSuchAlgorithmException {
            SecureRandom sr = SecureRandom.getInstanceStrong();
            sr.setSeed(sr.generateSeed(1048576));
            return new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2Y,
                    31,
                    sr);
        }

}
