package com.nextdv.infrastructure.platform;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 클래스명: PlatformJpaRepository
 * 작성자: JBumLee
 *
 * platforms 테이블 JPA 저장소 인터페이스
 */
public interface PlatformJpaRepository
    extends
      JpaRepository<PlatformEntity, UUID> {
}
