package com.nextdv.infrastructure.notification;

import com.nextdv.domain.channel.SlackSender;
import com.nextdv.domain.healthcheck.ServiceStatus;
import com.nextdv.domain.platform.Platform;
import java.util.ArrayList;
import java.util.List;

public class FakeSlackSender implements SlackSender {

  private final List<String> sentUrls = new ArrayList<>();

  @Override
  public void send(String webhookUrl, Platform platform, ServiceStatus newStatus) {
    sentUrls.add(webhookUrl);
  }

  public List<String> getSentUrls() {
    return sentUrls;
  }
}
