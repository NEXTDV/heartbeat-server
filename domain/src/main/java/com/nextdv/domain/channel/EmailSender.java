package com.nextdv.domain.channel;

import com.nextdv.domain.healthcheck.ServiceStatus;
import com.nextdv.domain.platform.Platform;

public interface EmailSender {

  void send(String address, Platform platform, ServiceStatus newStatus);
}
