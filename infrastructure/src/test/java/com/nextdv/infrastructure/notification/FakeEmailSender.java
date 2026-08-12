package com.nextdv.infrastructure.notification;

import com.nextdv.domain.channel.EmailSender;
import com.nextdv.domain.healthcheck.ServiceStatus;
import com.nextdv.domain.platform.Platform;
import java.util.ArrayList;
import java.util.List;

public class FakeEmailSender implements EmailSender {

  private final List<String> sentAddresses = new ArrayList<>();

  @Override
  public void send(String address, Platform platform, ServiceStatus newStatus) {
    sentAddresses.add(address);
  }

  public List<String> getSentAddresses() {
    return sentAddresses;
  }
}
