package com.nextdv.infrastructure.channel;

import com.nextdv.domain.channel.Channel;
import com.nextdv.domain.channel.ChannelRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChannelRepositoryImpl implements ChannelRepository {

  private final ChannelJpaRepository channelJpaRepository;

  @Override
  public Channel save(Channel channel) {
    ChannelEntity saved = channelJpaRepository.save(toEntity(channel));
    return toDomain(saved);
  }

  @Override
  public List<Channel> findAllByUserId(UUID userId) {
    return channelJpaRepository.findAllByUserIdAndDeletedAtIsNull(userId).stream()
        .map(this::toDomain)
        .toList();
  }

  @Override
  public Optional<Channel> findById(UUID id) {
    return channelJpaRepository.findById(id).map(this::toDomain);
  }

  private ChannelEntity toEntity(Channel channel) {
    return new ChannelEntity(
        channel.getId(),
        channel.getUserId(),
        channel.getType(),
        channel.getName(),
        channel.getConfig(),
        channel.getCreatedAt(),
        channel.getUpdatedAt(),
        channel.getDeletedAt()
    );
  }

  private Channel toDomain(ChannelEntity entity) {
    return new Channel(
        entity.getId(),
        entity.getUserId(),
        entity.getType(),
        entity.getName(),
        entity.getConfig(),
        entity.getCreatedAt(),
        entity.getUpdatedAt(),
        entity.getDeletedAt()
    );
  }
}
