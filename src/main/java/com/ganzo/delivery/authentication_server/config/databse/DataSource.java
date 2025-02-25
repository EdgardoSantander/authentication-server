package com.ganzo.delivery.authentication_server.config.databse;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.ganzo.libreries.repository")
@EntityScan(basePackages = "com.ganzo.libreries.entity")
public class DataSource {
}
