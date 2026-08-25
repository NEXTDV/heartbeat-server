package com.nextdv.domain.channel;

import com.nextdv.domain.platform.PlatformRepository;
import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * class: ChannelPlatformService 작성자: JBumLee
 *
 * 채널-플랫폼 구독 비즈니스 로직을 담당하는 서비스
 */
@Service
@RequiredArgsConstructor
public class ChannelPlatformService {

  private final ChannelPlatformRepository channelPlatformRepository;
  private final ChannelRepository channelRepository;
  private final PlatformRepository platformRepository;

  /**
   * 메소드이름: subscribe
   *
   * @parameter: channelId - 구독할 채널 UUID, platformId - 구독할 플랫폼 UUID
   * @return: 생성된 채널-플랫폼 구독 객체
   *
   *          채널과 플랫폼이 존재하고 중복 구독이 아닌 경우 구독을 생성한다
   */
  public ChannelPlatform subscribe(UUID channelId, UUID platformId) {
    channelRepository
        .findById(channelId)
        .orElseThrow(() -> new NoSuchElementException("채널을 찾을 수 없습니다."));
    platformRepository
        .findById(platformId)
        .orElseThrow(() -> new NoSuchElementException("플랫폼을 찾을 수 없습니다."));
    if (channelPlatformRepository.existsByChannelIdAndPlatformId(
        channelId,
        platformId
    )) {
      throw new IllegalStateException("이미 구독 중입니다.");
    }
    return channelPlatformRepository.save(
        new ChannelPlatform(UUID.randomUUID(), channelId, platformId, Instant.now())
    );
  }
}
