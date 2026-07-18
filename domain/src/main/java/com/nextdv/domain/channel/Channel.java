package com.nextdv.domain.channel;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import lombok.Getter;

@Getter
public class Channel {

  private final UUID id;
  private final UUID userId;
  private final ChannelType type;
  private final String name;
  private final Map<String, Object> config;
  private final Instant createdAt;
  private final Instant updatedAt;
  private final Instant deletedAt;

  public Channel(
      UUID id,
      UUID userId,
      ChannelType type,
      String name,
      Map<String, Object> config,
      Instant createdAt,
      Instant updatedAt,
      Instant deletedAt) {
    this.id = id;
    this.userId = userId;
    this.type = type;
    this.name = name;
    this.config = config;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.deletedAt = deletedAt;
  }
}
