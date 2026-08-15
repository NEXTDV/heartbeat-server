package com.nextdv.infrastructure.healthcheck;

import static org.assertj.core.api.Assertions.assertThat;

import com.nextdv.domain.channel.ChannelRepository;
import com.nextdv.domain.healthcheck.ServiceStatus;
import com.nextdv.domain.platform.Platform;
import com.nextdv.domain.platform.ServiceCategory;
import com.nextdv.infrastructure.channel.ChannelEntity;
import com.nextdv.infrastructure.channel.ChannelJpaRepository;
import com.nextdv.infrastructure.channel.ChannelPlatformEntity;
import com.nextdv.infrastructure.channel.ChannelPlatformJpaRepository;
import com.nextdv.infrastructure.channel.ChannelRepositoryImpl;
import com.nextdv.infrastructure.channel.ChannelTypeEntity;
import com.nextdv.infrastructure.notification.FakeDiscordSender;
import com.nextdv.infrastructure.notification.FakeEmailSender;
import com.nextdv.infrastructure.notification.FakeSlackSender;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import(ChannelRepositoryImpl.class)
@Testcontainers
class HealthCheckPollNotificationTest {

  @Container
  @ServiceConnection
  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

  @Autowired
  private ChannelJpaRepository channelJpaRepository;

  @Autowired
  private ChannelPlatformJpaRepository channelPlatformJpaRepository;

  @Autowired
  private ChannelRepository channelRepository;

  private FakeEmailSender fakeEmailSender;
  private FakeSlackSender fakeSlackSender;
  private FakeDiscordSender fakeDiscordSender;
  private HealthCheckPollService service;

  @BeforeEach
  void setUp() {
    fakeEmailSender = new FakeEmailSender();
    fakeSlackSender = new FakeSlackSender();
    fakeDiscordSender = new FakeDiscordSender();
    service = new HealthCheckPollService(
        null, null, null, channelRepository, fakeEmailSender, fakeSlackSender, fakeDiscordSender
    );
  }

  @Test
  void 상태변화_시_이메일_채널에_알림이_발송된다() {
    UUID platformId = UUID.randomUUID();
    UUID channelId = UUID.randomUUID();
    Instant now = Instant.now();

    channelJpaRepository.save(
        new ChannelEntity(
            channelId, UUID.randomUUID(), ChannelTypeEntity.EMAIL,
            "이메일", Map.of(
                "address",
                "user@example.com"
            ), now, now, null
        )
    );
    channelPlatformJpaRepository.save(
        new ChannelPlatformEntity(UUID.randomUUID(), channelId, platformId, now)
    );

    Platform platform = new Platform(
        platformId, "GitHub", ServiceCategory.DEVTOOL,
        "https://github.com", 5000, 2000, null, true
    );

    service.notifyStatusChange(
        platform,
        ServiceStatus.OPERATIONAL,
        ServiceStatus.MAJOR_OUTAGE
    );

    assertThat(fakeEmailSender.getSentAddresses()).containsExactly("user@example.com");
  }

  @Test
  void 상태가_같으면_알림이_발송되지_않는다() {
    Platform platform = new Platform(
        UUID.randomUUID(), "GitHub", ServiceCategory.DEVTOOL,
        "https://github.com", 5000, 2000, null, true
    );

    service.notifyStatusChange(
        platform,
        ServiceStatus.OPERATIONAL,
        ServiceStatus.OPERATIONAL
    );

    assertThat(fakeEmailSender.getSentAddresses()).isEmpty();
  }

  @Test
  void 이전_상태가_없으면_알림이_발송되지_않는다() {
    Platform platform = new Platform(
        UUID.randomUUID(), "GitHub", ServiceCategory.DEVTOOL,
        "https://github.com", 5000, 2000, null, true
    );

    service.notifyStatusChange(
        platform,
        null,
        ServiceStatus.MAJOR_OUTAGE
    );

    assertThat(fakeEmailSender.getSentAddresses()).isEmpty();
  }

  @Test
  void 소프트삭제된_구독은_알림이_발송되지_않는다() {
    UUID platformId = UUID.randomUUID();
    UUID channelId = UUID.randomUUID();
    Instant now = Instant.now();

    channelJpaRepository.save(
        new ChannelEntity(
            channelId, UUID.randomUUID(), ChannelTypeEntity.EMAIL,
            "이메일", Map.of(
                "address",
                "user@example.com"
            ), now, now, null
        )
    );
    channelPlatformJpaRepository.save(
        new ChannelPlatformEntity(UUID.randomUUID(), channelId, platformId, now, now)
    );

    Platform platform = new Platform(
        platformId, "GitHub", ServiceCategory.DEVTOOL,
        "https://github.com", 5000, 2000, null, true
    );

    service.notifyStatusChange(
        platform,
        ServiceStatus.OPERATIONAL,
        ServiceStatus.MAJOR_OUTAGE
    );

    assertThat(fakeEmailSender.getSentAddresses()).isEmpty();
  }

  @Test
  void 이미_발송된_상태에서_같은_상태가_유지되면_추가_알림이_발송되지_않는다() {
    UUID platformId = UUID.randomUUID();
    UUID channelId = UUID.randomUUID();
    Instant now = Instant.now();

    channelJpaRepository.save(
        new ChannelEntity(
            channelId, UUID.randomUUID(), ChannelTypeEntity.EMAIL,
            "이메일", Map.of(
                "address",
                "user@example.com"
            ), now, now, null
        )
    );
    channelPlatformJpaRepository.save(
        new ChannelPlatformEntity(UUID.randomUUID(), channelId, platformId, now)
    );

    Platform platform = new Platform(
        platformId, "GitHub", ServiceCategory.DEVTOOL,
        "https://github.com", 5000, 2000, null, true
    );

    service.notifyStatusChange(
        platform,
        ServiceStatus.OPERATIONAL,
        ServiceStatus.MAJOR_OUTAGE
    );
    service.notifyStatusChange(
        platform,
        ServiceStatus.MAJOR_OUTAGE,
        ServiceStatus.MAJOR_OUTAGE
    );

    assertThat(fakeEmailSender.getSentAddresses()).hasSize(1);
  }

  @Test
  void 상태변화_시_Slack_채널에_알림이_발송된다() {
    UUID platformId = UUID.randomUUID();
    UUID channelId = UUID.randomUUID();
    Instant now = Instant.now();

    channelJpaRepository.save(
        new ChannelEntity(
            channelId, UUID.randomUUID(), ChannelTypeEntity.SLACK,
            "슬랙", Map.of("url", "https://hooks.slack.com/test"), now, now, null
        )
    );
    channelPlatformJpaRepository.save(
        new ChannelPlatformEntity(UUID.randomUUID(), channelId, platformId, now)
    );

    Platform platform = new Platform(
        platformId, "GitHub", ServiceCategory.DEVTOOL,
        "https://github.com", 5000, 2000, null, true
    );

    service.notifyStatusChange(platform, ServiceStatus.OPERATIONAL, ServiceStatus.MAJOR_OUTAGE);

    assertThat(fakeSlackSender.getSentUrls()).containsExactly("https://hooks.slack.com/test");
  }

  @Test
  void 상태변화_시_Discord_채널에_알림이_발송된다() {
    UUID platformId = UUID.randomUUID();
    UUID channelId = UUID.randomUUID();
    Instant now = Instant.now();

    channelJpaRepository.save(
        new ChannelEntity(
            channelId, UUID.randomUUID(), ChannelTypeEntity.DISCORD,
            "디스코드", Map.of("url", "https://discord.com/api/webhooks/test"), now, now, null
        )
    );
    channelPlatformJpaRepository.save(
        new ChannelPlatformEntity(UUID.randomUUID(), channelId, platformId, now)
    );

    Platform platform = new Platform(
        platformId, "GitHub", ServiceCategory.DEVTOOL,
        "https://github.com", 5000, 2000, null, true
    );

    service.notifyStatusChange(platform, ServiceStatus.OPERATIONAL, ServiceStatus.MAJOR_OUTAGE);

    assertThat(fakeDiscordSender.getSentUrls()).containsExactly(
        "https://discord.com/api/webhooks/test"
    );
  }
}
