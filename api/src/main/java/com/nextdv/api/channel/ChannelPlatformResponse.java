package com.nextdv.api.channel;

import com.nextdv.domain.channel.ChannelPlatform;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChannelPlatformResponse {

  private UUID id;
  private UUID channelId;
  private UUID platformId;
  private Instant createdAt;

  public static ChannelPlatformResponse from(ChannelPlatform channelPlatform) {
    return new ChannelPlatformResponse(
        channelPlatform.getId(),
        channelPlatform.getChannelId(),
        channelPlatform.getPlatformId(),
        channelPlatform.getCreatedAt()
    );
  }
}
