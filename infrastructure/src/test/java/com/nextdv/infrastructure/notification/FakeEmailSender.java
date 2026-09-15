package com.nextdv.infrastructure.notification;

import com.nextdv.domain.channel.EmailSender;
import com.nextdv.domain.healthcheck.ServiceStatus;
import com.nextdv.domain.platform.Platform;
import java.util.ArrayList;
import java.util.List;

public class FakeEmailSender implements EmailSender {

  private final List<String> sentAddresses = new ArrayList<>();
  private boolean shouldFail = false;

  @Override
  public void send(String address, Platform platform, ServiceStatus newStatus) {
    if (shouldFail) {
      throw new RuntimeException("SMTP 오류 (테스트용)");
    }
    sentAddresses.add(address);
  }

  public List<String> getSentAddresses() {
    return sentAddresses;
  }

  public void setShouldFail(boolean shouldFail) {
    this.shouldFail = shouldFail;
  }
}
