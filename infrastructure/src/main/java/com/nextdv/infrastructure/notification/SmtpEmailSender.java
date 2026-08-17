package com.nextdv.infrastructure.notification;

import com.nextdv.domain.channel.EmailSender;
import com.nextdv.domain.healthcheck.ServiceStatus;
import com.nextdv.domain.platform.Platform;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SmtpEmailSender implements EmailSender {

  private final JavaMailSender mailSender;

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
