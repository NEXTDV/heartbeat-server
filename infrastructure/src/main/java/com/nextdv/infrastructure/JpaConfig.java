package com.nextdv.infrastructure;

import com.nextdv.infrastructure.platform.PlatformProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan("com.nextdv.infrastructure")
@EnableJpaRepositories("com.nextdv.infrastructure")
@EnableConfigurationProperties(PlatformProperties.class)
public class JpaConfig {
}
