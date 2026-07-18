package com.nextdv.infrastructure.channel;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelJpaRepository extends JpaRepository<ChannelEntity, UUID> {

  List<ChannelEntity> findAllByUserIdAndDeletedAtIsNull(UUID userId);
}
