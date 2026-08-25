package com.nextdv.domain.channel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * class: ChannelRepository 작성자: JBumLee
 *
 * 알림 채널 도메인 저장소 인터페이스
 */
public interface ChannelRepository {

  /**
   * 메소드이름: save
   *
   * @parameter: channel - 저장할 채널 객체
   * @return: 저장된 채널 객체
   *
   *          채널을 저장한다
   */
  Channel save(Channel channel);

  /**
   * 메소드이름: findAllByUserId
   *
   * @parameter: userId - 조회할 사용자 UUID
   * @return: 해당 사용자의 채널 목록
   *
   *          특정 사용자의 삭제되지 않은 채널 목록을 조회한다
   */
  List<Channel> findAllByUserId(UUID userId);

  /**
   * 메소드이름: findById
   *
   * @parameter: id - 조회할 채널 UUID
   * @return: 채널 객체 (없으면 empty)
   *
   *          ID로 채널을 조회한다
   */
  Optional<Channel> findById(UUID id);

  /**
   * 메소드이름: findEmailChannelsByPlatformId
   *
   * @parameter: platformId - 조회할 플랫폼 UUID
   * @return: 해당 플랫폼을 구독 중인 이메일 채널 목록
   *
   *          특정 플랫폼을 구독 중인 이메일 채널을 조회한다
   */
  List<Channel> findEmailChannelsByPlatformId(UUID platformId);

  /**
   * 메소드이름: findSlackChannelsByPlatformId
   *
   * @parameter: platformId - 조회할 플랫폼 UUID
   * @return: 해당 플랫폼을 구독 중인 Slack 채널 목록
   *
   *          특정 플랫폼을 구독 중인 Slack 채널을 조회한다
   */
  List<Channel> findSlackChannelsByPlatformId(UUID platformId);

  /**
   * 메소드이름: findDiscordChannelsByPlatformId
   *
   * @parameter: platformId - 조회할 플랫폼 UUID
   * @return: 해당 플랫폼을 구독 중인 Discord 채널 목록
   *
   *          특정 플랫폼을 구독 중인 Discord 채널을 조회한다
   */
  List<Channel> findDiscordChannelsByPlatformId(UUID platformId);
}
