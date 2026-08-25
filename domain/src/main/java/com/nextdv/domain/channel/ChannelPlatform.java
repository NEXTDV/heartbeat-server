package com.nextdv.domain.channel;

import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * class: ChannelPlatform 작성자: JBumLee
 *
 * 채널-플랫폼 구독 관계 도메인 객체
 */
@Getter
@AllArgsConstructor
public class ChannelPlatform {

  private final UUID id;
  private final UUID channelId;
  private final UUID platformId;
  private final Instant createdAt;
}
