package com.nextdv.infrastructure.notification;

import com.nextdv.domain.channel.DiscordSender;
import com.nextdv.domain.healthcheck.ServiceStatus;
import com.nextdv.domain.platform.Platform;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

/**
 * 클래스명: DiscordWebhookSender
 * 작성자: JBumLee
 *
 * Discord 웹훅을 통해 플랫폼 상태 변화 알림을 발송하는 구현체
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DiscordWebhookSender implements DiscordSender {

  private final RestClient restClient;

  /**
   * 메소드이름: send
   * 플랫폼 상태 변화 메시지를 Discord 채널에 POST 요청으로 발송한다
   *
   * @param webhookUrl Discord 웹훅 URL, platform - 상태 변화된 플랫폼, newStatus -
   */
  @Override
  public void send(String webhookUrl, Platform platform, ServiceStatus newStatus) {
    String content = "[Heartbeat] " + platform.getName() + " 상태 변화: " + newStatus.name();
    restClient
        .post()
        .uri(webhookUrl)
        .body(
            Map.of(
                "content",
                content
            )
        )
        .retrieve()
        .toBodilessEntity();
  }
}
