package com.nextdv.infrastructure.channel;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChannelJpaRepository extends JpaRepository<ChannelEntity, UUID> {

  List<ChannelEntity> findAllByUserIdAndDeletedAtIsNull(UUID userId);

  @Query("SELECT c FROM ChannelEntity c "
      + "WHERE c.id IN ("
      + "  SELECT cp.channelId FROM ChannelPlatformEntity cp "
      + "  WHERE cp.platformId = :platformId AND cp.deletedAt IS NULL"
      + ") AND c.deletedAt IS NULL AND c.type = :type")
  List<ChannelEntity> findEmailChannelsByPlatformId(
      @Param("platformId") UUID platformId, @Param("type") ChannelTypeEntity type);
}
