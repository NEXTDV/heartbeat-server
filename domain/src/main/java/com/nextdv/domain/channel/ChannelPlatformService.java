package com.nextdv.domain.channel;

import com.nextdv.domain.platform.PlatformRepository;
import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 클래스명: ChannelPlatformService
 * 작성자: JBumLee
 *
 * 채널-플랫폼 구독 비즈니스 로직을 담당하는 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ChannelPlatformService {

  private final ChannelPlatformRepository channelPlatformRepository;
  private final ChannelRepository channelRepository;
  private final PlatformRepository platformRepository;

  /**
   * 메소드이름: subscribe
   * 채널과 플랫폼이 존재하고 중복 구독이 아닌 경우 구독을 생성한다
   *
   * @param channelId 구독할 채널 UUID, platformId - 구독할 플랫폼 UUID
   * @return 생성된 채널-플랫폼 구독 객체
   */
  public ChannelPlatform subscribe(UUID channelId, UUID platformId) {
    channelRepository
        .findById(channelId)
        .orElseThrow(() -> {
          log.warn(
              "구독 실패 — 존재하지 않는 channelId: {}",
              channelId
          );
          return new NoSuchElementException("채널을 찾을 수 없습니다.");
        });
    platformRepository
        .findById(platformId)
        .orElseThrow(() -> {
          log.warn(
              "구독 실패 — 존재하지 않는 platformId: {}",
              platformId
          );
          return new NoSuchElementException("플랫폼을 찾을 수 없습니다.");
        });
    if (channelPlatformRepository.existsByChannelIdAndPlatformId(
        channelId,
        platformId
    )) {
      log.warn(
          "구독 실패 — 이미 구독 중 channelId: {}, platformId: {}",
          channelId,
          platformId
      );
      throw new IllegalStateException("이미 구독 중입니다.");
    }
    ChannelPlatform saved = channelPlatformRepository.save(
        new ChannelPlatform(UUID.randomUUID(), channelId, platformId, Instant.now())
    );
    log.info(
        "구독 완료 — id: {}, channelId: {}, platformId: {}",
        saved.getId(),
        channelId,
        platformId
    );
    return saved;
  }
}
