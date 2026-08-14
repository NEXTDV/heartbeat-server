package com.nextdv.domain.channel;

import java.util.UUID;

public interface ChannelPlatformRepository {

  ChannelPlatform save(ChannelPlatform channelPlatform);

  boolean existsByChannelIdAndPlatformId(UUID channelId, UUID platformId);
}
