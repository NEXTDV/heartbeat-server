package com.nextdv.infrastructure.channel;

import com.nextdv.domain.channel.ChannelPlatform;
import com.nextdv.domain.channel.ChannelPlatformRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChannelPlatformRepositoryImpl implements ChannelPlatformRepository {

  private final ChannelPlatformJpaRepository jpaRepository;

  @Override
  public boolean existsByChannelIdAndPlatformId(UUID channelId, UUID platformId) {
    return jpaRepository.existsByChannelIdAndPlatformIdAndDeletedAtIsNull(
        channelId,
        platformId
    );
  }

  @Override
  public Optional<ChannelPlatform> findByChannelIdAndPlatformId(UUID channelId, UUID platformId) {
    return jpaRepository
        .findByChannelIdAndPlatformIdAndDeletedAtIsNull(
            channelId,
            platformId
        )
        .map(
            entity -> new ChannelPlatform(
                entity.getId(),
                entity.getChannelId(),
                entity.getPlatformId(),
                entity.getCreatedAt()
            )
        );
  }

  @Override
  public ChannelPlatform save(ChannelPlatform channelPlatform) {
    ChannelPlatformEntity entity = new ChannelPlatformEntity(
        channelPlatform.getId(),
        channelPlatform.getChannelId(),
        channelPlatform.getPlatformId(),
        channelPlatform.getCreatedAt()
    );
    ChannelPlatformEntity saved = jpaRepository.save(entity);
    return new ChannelPlatform(
        saved.getId(), saved.getChannelId(), saved.getPlatformId(), saved.getCreatedAt()
    );
  }
}
