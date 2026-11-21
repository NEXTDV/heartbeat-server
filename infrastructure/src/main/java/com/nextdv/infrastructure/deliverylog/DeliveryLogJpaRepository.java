package com.nextdv.infrastructure.deliverylog;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryLogJpaRepository extends JpaRepository<DeliveryLogEntity, UUID> {
}
