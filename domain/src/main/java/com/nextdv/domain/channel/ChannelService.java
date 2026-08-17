package com.nextdv.domain.channel;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChannelService {

  private final ChannelRepository channelRepository;

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
    if (config == null) {
      throw new IllegalArgumentException("config는 필수입니다.");
    }
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

  public List<Channel> findAllByUserId(UUID userId) {
    return channelRepository.findAllByUserId(userId);
  }

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
