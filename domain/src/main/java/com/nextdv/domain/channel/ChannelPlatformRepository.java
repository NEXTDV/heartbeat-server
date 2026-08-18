package com.nextdv.domain.channel;

import java.util.Optional;
import java.util.UUID;

public interface ChannelPlatformRepository {

  ChannelPlatform save(ChannelPlatform channelPlatform);

  boolean existsByChannelIdAndPlatformId(UUID channelId, UUID platformId);

  Optional<ChannelPlatform> findByChannelIdAndPlatformId(UUID channelId, UUID platformId);
}
