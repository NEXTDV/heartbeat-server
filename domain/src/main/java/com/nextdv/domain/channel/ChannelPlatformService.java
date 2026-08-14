package com.nextdv.domain.channel;

import java.time.Instant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChannelPlatformService {

  private final ChannelPlatformRepository channelPlatformRepository;

  public ChannelPlatform subscribe(UUID channelId, UUID platformId) {
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
