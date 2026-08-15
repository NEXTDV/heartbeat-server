package com.nextdv.domain.channel;

import com.nextdv.domain.healthcheck.ServiceStatus;
import com.nextdv.domain.platform.Platform;

public interface DiscordSender {

  void send(String webhookUrl, Platform platform, ServiceStatus newStatus);
}
