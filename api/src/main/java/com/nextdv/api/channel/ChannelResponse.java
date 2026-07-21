package com.nextdv.api.channel;

import com.nextdv.domain.channel.ChannelType;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public class ChannelResponse {

  private final UUID id;
  private final UUID userId;
  private final ChannelType type;
  private final String name;
  private final Map<String, Object> config;
  private final Instant createdAt;

  public ChannelResponse(
      UUID id,
      UUID userId,
      ChannelType type,
      String name,
      Map<String, Object> config,
      Instant createdAt) {
    this.id = id;
    this.userId = userId;
    this.type = type;
    this.name = name;
    this.config = config;
    this.createdAt = createdAt;
  }

  public UUID getId() {
    return id;
  }

  public UUID getUserId() {
    return userId;
  }

  public ChannelType getType() {
    return type;
  }

  public String getName() {
    return name;
  }

  public Map<String, Object> getConfig() {
    return config;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }
}
