package com.nextdv.infrastructure.notification;

import com.nextdv.domain.channel.SlackSender;
import com.nextdv.domain.healthcheck.ServiceStatus;
import com.nextdv.domain.platform.Platform;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

/**
 * 클래스명: SlackWebhookSender
 * 작성자: JBumLee
 *
 * Slack 웹훅을 통해 플랫폼 상태 변화 알림을 발송하는 구현체
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SlackWebhookSender implements SlackSender {

  private final RestClient restClient;

  /**
   * 메소드이름: send
   * 플랫폼 상태 변화 메시지를 Slack 채널에 POST 요청으로 발송한다
   *
   * @param webhookUrl Slack 웹훅 URL, platform - 상태 변화된 플랫폼, newStatus - 변경된
   */
  @Override
  public void send(String webhookUrl, Platform platform, ServiceStatus newStatus) {
    log.info(
        "Slack 알림 발송 — 플랫폼: {}, 상태: {}",
        platform.getName(),
        newStatus.name()
    );
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
    log.info(
        "Slack 알림 발송 완료 — 플랫폼: {}",
        platform.getName()
    );
  }
}
