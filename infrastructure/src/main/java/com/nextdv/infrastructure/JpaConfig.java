package com.nextdv.infrastructure;

import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan("com.nextdv.infrastructure")
@EnableJpaRepositories("com.nextdv.infrastructure")
public class JpaConfig {
}
