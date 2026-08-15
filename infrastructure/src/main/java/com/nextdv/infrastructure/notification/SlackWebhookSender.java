package com.nextdv.infrastructure.notification;

import com.nextdv.domain.channel.SlackSender;
import com.nextdv.domain.healthcheck.ServiceStatus;
import com.nextdv.domain.platform.Platform;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class SlackWebhookSender implements SlackSender {

  private final RestClient restClient;

  @Override
  public void send(String webhookUrl, Platform platform, ServiceStatus newStatus) {
    String text = "[Heartbeat] " + platform.getName() + " 상태 변화: " + newStatus.name();
    restClient
        .post()
        .uri(webhookUrl)
        .body(
            Map.of(
                "text",
                text
            )
        )
        .retrieve()
        .toBodilessEntity();
  }
}
