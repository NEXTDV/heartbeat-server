package com.nextdv.domain.channel;

import java.util.Optional;
import java.util.UUID;

/**
 * 클래스명: ChannelPlatformRepository
 * 작성자: JBumLee
 *
 * 채널-플랫폼 구독 도메인 저장소 인터페이스
 */
public interface ChannelPlatformRepository {

  /**
   * 메소드이름: save
   * 채널-플랫폼 구독 정보를 저장한다
   *
   * @param channelPlatform 저장할 채널-플랫폼 구독 객체
   * @return 저장된 채널-플랫폼 구독 객체
   */
  ChannelPlatform save(ChannelPlatform channelPlatform);

  /**
   * 메소드이름: existsByChannelIdAndPlatformId
   * 해당 채널과 플랫폼 조합의 활성 구독이 존재하는지 확인한다
   *
   * @param channelId 채널 UUID, platformId - 플랫폼 UUID
   * @return 구독 존재 여부
   */
  boolean existsByChannelIdAndPlatformId(UUID channelId, UUID platformId);

  Optional<ChannelPlatform> findByChannelIdAndPlatformId(UUID channelId, UUID platformId);
}
