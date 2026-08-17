package com.nextdv.api.channel;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Getter;

@Getter
public class ChannelPlatformRequest {

  @NotNull(message = "channelId는 필수입니다.")
  private UUID channelId;

  @NotNull(message = "platformId는 필수입니다.")
  private UUID platformId;
}
