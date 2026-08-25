package com.nextdv.infrastructure.healthcheck;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 클래스명: HealthCheckLogJpaRepository
 * 작성자: JBumLee
 *
 * health_check_logs 테이블 JPA 저장소 인터페이스
 */
public interface HealthCheckLogJpaRepository extends JpaRepository<HealthCheckLogEntity, UUID> {

  List<HealthCheckLogEntity> findAllByPlatformId(UUID platformId);

  Optional<HealthCheckLogEntity> findTopByPlatformIdOrderByCheckedAtDesc(UUID platformId);
}
