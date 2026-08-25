package com.nextdv.infrastructure.notification;

import com.nextdv.domain.channel.EmailSender;
import com.nextdv.domain.healthcheck.ServiceStatus;
import com.nextdv.domain.platform.Platform;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * class: SmtpEmailSender 작성자: JBumLee
 *
 * SMTP를 통해 플랫폼 상태 변화 알림 이메일을 발송하는 구현체
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SmtpEmailSender implements EmailSender {

  private final JavaMailSender mailSender;

  /**
   * 메소드이름: send
   *
   * @parameter: address - 수신자 이메일 주소, platform - 상태 변화된 플랫폼, newStatus - 변경된 상태
   *
   *             플랫폼 상태 변화 내용을 이메일로 발송한다
   */
  @Override
  public void send(String address, Platform platform, ServiceStatus newStatus) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(address);
    message.setSubject("[Heartbeat] " + platform.getName() + " 상태 변화: " + newStatus.name());
    message.setText(
        platform.getName() + " 서비스 상태가 " + newStatus.name() + "으로 변경되었습니다.\n"
            + "URL: " + platform.getHealthCheckUrl()
    );
    mailSender.send(message);
  }
}
