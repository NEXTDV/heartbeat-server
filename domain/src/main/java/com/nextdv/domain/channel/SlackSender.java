package com.nextdv.domain.channel;

import com.nextdv.domain.healthcheck.ServiceStatus;
import com.nextdv.domain.platform.Platform;

/**
 * 클래스명: SlackSender
 * 작성자: JBumLee
 *
 * Slack 웹훅 알림 발송 인터페이스
 */
public interface SlackSender {

  /**
   * 메소드이름: send
   * 플랫폼 상태 변화를 Slack 채널에 알림으로 발송한다
   *
   * @param webhookUrl Slack 웹훅 URL, platform - 상태 변화된 플랫폼, newStatus - 변경된
   */
  void send(String webhookUrl, Platform platform, ServiceStatus newStatus);
}
