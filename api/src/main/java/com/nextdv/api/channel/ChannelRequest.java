package com.nextdv.api.channel;

import com.nextdv.domain.channel.ChannelType;
import java.util.Map;
import java.util.UUID;
import lombok.Getter;

@Getter
public class ChannelRequest {

  private UUID userId;
  private ChannelType type;
  private String name;
  private Map<String, Object> config;
}
