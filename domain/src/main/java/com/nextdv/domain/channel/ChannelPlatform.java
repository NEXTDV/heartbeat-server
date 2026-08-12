package com.nextdv.domain.channel;

import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChannelPlatform {

  private final UUID id;
  private final UUID channelId;
  private final UUID platformId;
  private final Instant createdAt;
}
