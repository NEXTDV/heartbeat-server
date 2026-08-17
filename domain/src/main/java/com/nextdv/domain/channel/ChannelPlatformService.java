package com.nextdv.domain.channel;

import com.nextdv.domain.platform.PlatformRepository;
import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChannelPlatformService {

  private final ChannelPlatformRepository channelPlatformRepository;
  private final ChannelRepository channelRepository;
  private final PlatformRepository platformRepository;

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
