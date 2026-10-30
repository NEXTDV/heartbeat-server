package com.nextdv.infrastructure.deliverylog;

import com.nextdv.domain.deliverylog.DeliveryLog;
import com.nextdv.domain.deliverylog.DeliveryLogRepository;
import com.nextdv.domain.deliverylog.DeliveryStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DeliveryLogRepositoryImpl implements DeliveryLogRepository {

  private final DeliveryLogJpaRepository jpaRepository;

  @Override
  public DeliveryLog save(DeliveryLog log) {
    DeliveryLogEntity entity = new DeliveryLogEntity(
        log.getId(),
        log.getChannelPlatformId(),
        DeliveryStatusEntity.valueOf(log.getStatus().name()),
        log.getDeliveredAt()
    );
    DeliveryLogEntity saved = jpaRepository.save(entity);
    return new DeliveryLog(
        saved.getId(),
        saved.getChannelPlatformId(),
        DeliveryStatus.valueOf(saved.getStatus().name()),
        saved.getDeliveredAt()
    );
  }
}
