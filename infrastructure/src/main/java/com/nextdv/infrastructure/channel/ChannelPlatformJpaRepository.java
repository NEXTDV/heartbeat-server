package com.nextdv.infrastructure.channel;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelPlatformJpaRepository extends JpaRepository<ChannelPlatformEntity, UUID> {

  boolean existsByChannelIdAndPlatformIdAndDeletedAtIsNull(UUID channelId, UUID platformId);
}
