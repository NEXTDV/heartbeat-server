package com.nextdv.domain.channel;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * class: ChannelService 작성자: JBumLee
 *
 * 알림 채널 생성, 조회, 삭제 비즈니스 로직을 담당하는 서비스
 */
@Service
@RequiredArgsConstructor
public class ChannelService {

  private final ChannelRepository channelRepository;

  /**
   * 메소드이름: create
   *
   * @parameter: userId - 채널 소유자 UUID, type - 채널 타입, name - 채널 이름, config - 채널 설정값
   * @return: 생성된 채널 객체
   *
   *          채널 타입별 config 유효성 검증 후 채널을 생성한다
   */
  public Channel create(
      UUID userId, ChannelType type, String name, Map<String, Object> config) {
    validateConfig(
        type,
        config
    );
    Instant now = Instant.now();
    Channel channel = new Channel(UUID.randomUUID(), userId, type, name, config, now, now, null);
    return channelRepository.save(channel);
  }

  private void validateConfig(ChannelType type, Map<String, Object> config) {
    switch (type) {
      case EMAIL -> {
        Object address = config.get("address");
        if (!(address instanceof String s) || s.isBlank()) {
          throw new IllegalArgumentException("EMAIL 채널은 config.address가 필요합니다.");
        }
      }
      case SLACK -> {
        Object url = config.get("url");
        if (!(url instanceof String s) || s.isBlank()) {
          throw new IllegalArgumentException("SLACK 채널은 config.url이 필요합니다.");
        }
      }
      case DISCORD -> {
        Object url = config.get("url");
        if (!(url instanceof String s) || s.isBlank()) {
          throw new IllegalArgumentException("DISCORD 채널은 config.url이 필요합니다.");
        }
      }
      default -> {
      }
    }
  }

  /**
   * 메소드이름: findAllByUserId
   *
   * @parameter: userId - 조회할 사용자 UUID
   * @return: 해당 사용자의 채널 목록
   *
   *          특정 사용자의 삭제되지 않은 채널 목록을 조회한다
   */
  public List<Channel> findAllByUserId(UUID userId) {
    return channelRepository.findAllByUserId(userId);
  }

  /**
   * 메소드이름: delete
   *
   * @parameter: id - 삭제할 채널 UUID
   *
   *             채널을 소프트 삭제 처리한다 (deletedAt 설정)
   */
  public void delete(UUID id) {
    Channel channel = channelRepository
        .findById(id)
        .orElseThrow(() -> new NoSuchElementException("채널을 찾을 수 없습니다."));
    Channel deleted = new Channel(
        channel.getId(),
        channel.getUserId(),
        channel.getType(),
        channel.getName(),
        channel.getConfig(),
        channel.getCreatedAt(),
        Instant.now(),
        Instant.now()
    );
    channelRepository.save(deleted);
  }
}
