package com.nextdv.domain.channel;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChannelService {

  private final ChannelRepository channelRepository;

  public Channel create(
      UUID userId, ChannelType type, String name, Map<String, Object> config) {
    Instant now = Instant.now();
    Channel channel = new Channel(UUID.randomUUID(), userId, type, name, config, now, now, null);
    return channelRepository.save(channel);
  }

  public List<Channel> findAllByUserId(UUID userId) {
    return channelRepository.findAllByUserId(userId);
  }

  public void delete(UUID id) {
    Channel channel = channelRepository
        .findById(id)
        .orElseThrow(() -> new IllegalArgumentException("채널을 찾을 수 없습니다."));
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
