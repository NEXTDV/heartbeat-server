package com.nextdv.infrastructure.channel;

import com.nextdv.infrastructure.platform.PlatformEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;

@Getter
@Entity
@Table(name = "channel_platforms")
public class ChannelPlatformEntity {

  @Id
  private UUID id;

  @Column(name = "channel_id", nullable = false)
  private UUID channelId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "channel_id", insertable = false, updatable = false)
  private ChannelEntity channel;

  @Column(name = "platform_id", nullable = false)
  private UUID platformId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "platform_id", insertable = false, updatable = false)
  private PlatformEntity platform;

  @Column(name = "created_at", nullable = false)
  private Instant createdAt;

  @Column(name = "deleted_at")
  private Instant deletedAt;

  protected ChannelPlatformEntity() {
  }

  public ChannelPlatformEntity(UUID id, UUID channelId, UUID platformId, Instant createdAt) {
    this(id, channelId, platformId, createdAt, null);
  }

  public ChannelPlatformEntity(
      UUID id, UUID channelId, UUID platformId, Instant createdAt, Instant deletedAt) {
    this.id = id;
    this.channelId = channelId;
    this.platformId = platformId;
    this.createdAt = createdAt;
    this.deletedAt = deletedAt;
  }
}
