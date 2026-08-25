package com.nextdv.api.channel;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Getter;

/**
 * class: ChannelPlatformRequest 작성자: JBumLee
 *
 * 채널-플랫폼 구독 생성 요청 DTO
 */
@Getter
public class ChannelPlatformRequest {

  @NotNull(message = "channelId는 필수입니다.")
  private UUID channelId;

  @NotNull(message = "platformId는 필수입니다.")
  private UUID platformId;
}
