package com.nextdv.infrastructure.channel;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 클래스명: ChannelPlatformJpaRepository
 * 작성자: JBumLee
 *
 * channel_platforms 테이블 JPA 저장소 인터페이스
 */
public interface ChannelPlatformJpaRepository extends JpaRepository<ChannelPlatformEntity, UUID> {

  boolean existsByChannelIdAndPlatformIdAndDeletedAtIsNull(UUID channelId, UUID platformId);
}
