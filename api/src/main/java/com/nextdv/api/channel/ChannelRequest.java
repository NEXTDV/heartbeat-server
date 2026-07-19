package com.nextdv.api.channel;

import com.nextdv.domain.channel.ChannelType;
import java.util.Map;
import java.util.UUID;

public class ChannelRequest {

  private UUID userId;
  private ChannelType type;
  private String name;
  private Map<String, Object> config;

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
}
