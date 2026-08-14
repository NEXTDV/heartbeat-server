package com.nextdv.api.channel;

import java.util.UUID;
import lombok.Getter;

@Getter
public class ChannelPlatformRequest {

  private UUID channelId;
  private UUID platformId;
}
