package com.nextdv.infrastructure;

import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 클래스명: JpaConfig
 * 작성자: JBumLee
 *
 * JPA Entity 스캔 및 Repository 활성화 설정
 */
@Configuration
@EntityScan("com.nextdv.infrastructure")
@EnableJpaRepositories("com.nextdv.infrastructure")
public class JpaConfig {
}
