package com.nextdv.domain.channel;

import com.nextdv.domain.healthcheck.ServiceStatus;
import com.nextdv.domain.platform.Platform;

/**
 * 클래스명: EmailSender
 * 작성자: JBumLee
 *
 * 이메일 알림 발송 인터페이스
 */
public interface EmailSender {

  /**
   * 메소드이름: send
   * 플랫폼 상태 변화를 이메일로 발송한다
   *
   * @param address 수신자 이메일 주소, platform - 상태 변화된 플랫폼, newStatus - 변경된 상태
   */
  void send(String address, Platform platform, ServiceStatus newStatus);
}
