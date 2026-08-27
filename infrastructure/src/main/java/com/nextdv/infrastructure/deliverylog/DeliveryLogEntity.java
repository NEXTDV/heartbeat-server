package com.nextdv.infrastructure.deliverylog;

import com.nextdv.infrastructure.channel.ChannelPlatformEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "delivery_logs")
public class DeliveryLogEntity {

  @Id
  private UUID id;

  @Column(name = "channel_platform_id", nullable = false)
  private UUID channelPlatformId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "channel_platform_id", insertable = false, updatable = false)
  private ChannelPlatformEntity channelPlatform;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private DeliveryStatusEntity status;

  @Column(name = "delivered_at", nullable = false)
  private Instant deliveredAt;

  protected DeliveryLogEntity() {
  }

  public DeliveryLogEntity(
      UUID id, UUID channelPlatformId, DeliveryStatusEntity status, Instant deliveredAt) {
    this.id = id;
    this.channelPlatformId = channelPlatformId;
    this.status = status;
    this.deliveredAt = deliveredAt;
  }
}
