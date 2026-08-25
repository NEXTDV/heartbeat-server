package com.nextdv.api.channel;

import com.nextdv.domain.channel.ChannelPlatform;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 클래스명: ChannelPlatformResponse
 * 작성자: JBumLee
 *
 * 채널-플랫폼 구독 정보 응답 DTO
 */
@Getter
@AllArgsConstructor
public class ChannelPlatformResponse {

  private UUID id;
  private UUID channelId;
  private UUID platformId;
  private Instant createdAt;

  /**
   * 메소드이름: from
   * ChannelPlatform 도메인 객체를 응답 DTO로 변환한다
   *
   * @param channelPlatform 변환할 채널-플랫폼 도메인 객체
   * @return ChannelPlatformResponse DTO
   */
  public static ChannelPlatformResponse from(ChannelPlatform channelPlatform) {
    return new ChannelPlatformResponse(
        channelPlatform.getId(),
        channelPlatform.getChannelId(),
        channelPlatform.getPlatformId(),
        channelPlatform.getCreatedAt()
    );
  }
}
