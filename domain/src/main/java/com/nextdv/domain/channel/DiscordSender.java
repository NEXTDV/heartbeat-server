package com.nextdv.domain.channel;

import com.nextdv.domain.healthcheck.ServiceStatus;
import com.nextdv.domain.platform.Platform;

/**
 * 클래스명: DiscordSender
 * 작성자: JBumLee
 *
 * Discord 웹훅 알림 발송 인터페이스
 */
public interface DiscordSender {

  /**
   * 메소드이름: send
   * 플랫폼 상태 변화를 Discord 채널에 알림으로 발송한다
   *
   * @param webhookUrl Discord 웹훅 URL, platform - 상태 변화된 플랫폼, newStatus -
   */
  void send(String webhookUrl, Platform platform, ServiceStatus newStatus);
}
