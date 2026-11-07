package com.nextdv.domain.deliverylog;

import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeliveryLog {

  private final UUID id;
  private final UUID channelPlatformId;
  private final DeliveryStatus status;
  private final Instant deliveredAt;
}
