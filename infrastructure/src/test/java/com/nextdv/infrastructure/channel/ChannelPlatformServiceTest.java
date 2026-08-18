package com.nextdv.infrastructure.channel;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.nextdv.domain.channel.ChannelPlatform;
import com.nextdv.domain.channel.ChannelPlatformService;
import com.nextdv.domain.platform.ServiceCategory;
import com.nextdv.infrastructure.account.AccountEntity;
import com.nextdv.infrastructure.account.AccountJpaRepository;
import com.nextdv.infrastructure.platform.PlatformEntity;
import com.nextdv.infrastructure.platform.PlatformJpaRepository;
import com.nextdv.infrastructure.platform.PlatformRepositoryImpl;
import java.time.Instant;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
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
@Import({
    ChannelPlatformRepositoryImpl.class,
    ChannelPlatformService.class,
    ChannelRepositoryImpl.class,
    PlatformRepositoryImpl.class
})
@Testcontainers
class ChannelPlatformServiceTest {

  @Container
  @ServiceConnection
  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

  @Autowired
  private ChannelPlatformJpaRepository channelPlatformJpaRepository;

  @Autowired
  private ChannelJpaRepository channelJpaRepository;

  @Autowired
  private AccountJpaRepository accountJpaRepository;

  @Autowired
  private PlatformJpaRepository platformJpaRepository;

  @Autowired
  private ChannelPlatformService channelPlatformService;

  private UUID savedChannelId() {
    UUID userId = UUID.randomUUID();
    accountJpaRepository.save(new AccountEntity(userId, userId + "@test.com"));
    Instant now = Instant.now();
    ChannelEntity entity = channelJpaRepository.save(
        new ChannelEntity(
            UUID.randomUUID(),
            userId,
            ChannelTypeEntity.SLACK,
            "슬랙",
            Map.of(
                "url",
                "https://hooks.slack.com/test"
            ),
            now,
            now,
            null
        )
    );
    return entity.getId();
  }

  private UUID savedPlatformId() {
    PlatformEntity entity = platformJpaRepository.save(
        new PlatformEntity(
            UUID.randomUUID(),
            "테스트 플랫폼",
            ServiceCategory.OTHER,
            "https://example.com/health",
            3000,
            1000,
            null,
            true
        )
    );
    return entity.getId();
  }

  @Test
  void 채널과_플랫폼을_구독하면_저장하고_반환한다() {
    UUID channelId = savedChannelId();
    UUID platformId = savedPlatformId();

    ChannelPlatform result = channelPlatformService.subscribe(
        channelId,
        platformId
    );

    assertThat(result.getId()).isNotNull();
    assertThat(result.getChannelId()).isEqualTo(channelId);
    assertThat(result.getPlatformId()).isEqualTo(platformId);
    assertThat(result.getCreatedAt()).isNotNull();
  }

  @Test
  void 구독하면_DB에_실제로_저장된다() {
    UUID channelId = savedChannelId();
    UUID platformId = savedPlatformId();

    ChannelPlatform result = channelPlatformService.subscribe(
        channelId,
        platformId
    );

    ChannelPlatformEntity entity = channelPlatformJpaRepository.findById(result.getId())
        .orElseThrow();
    assertThat(entity.getChannelId()).isEqualTo(channelId);
    assertThat(entity.getPlatformId()).isEqualTo(platformId);
    assertThat(entity.getDeletedAt()).isNull();
  }

  @Test
  void 이미_구독된_채널_플랫폼을_다시_구독하면_예외가_발생한다() {
    UUID channelId = savedChannelId();
    UUID platformId = savedPlatformId();
    channelPlatformService.subscribe(
        channelId,
        platformId
    );

    assertThatThrownBy(
        () -> channelPlatformService.subscribe(
            channelId,
            platformId
        )
    )
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("이미 구독 중입니다.");
  }

  @Test
  void 존재하지_않는_채널ID로_구독하면_예외가_발생한다() {
    UUID channelId = UUID.randomUUID();
    UUID platformId = savedPlatformId();

    assertThatThrownBy(
        () -> channelPlatformService.subscribe(
            channelId,
            platformId
        )
    )
        .isInstanceOf(NoSuchElementException.class)
        .hasMessage("채널을 찾을 수 없습니다.");
  }

  @Test
  void 존재하지_않는_플랫폼ID로_구독하면_예외가_발생한다() {
    UUID channelId = savedChannelId();
    UUID platformId = UUID.randomUUID();

    assertThatThrownBy(
        () -> channelPlatformService.subscribe(
            channelId,
            platformId
        )
    )
        .isInstanceOf(NoSuchElementException.class)
        .hasMessage("플랫폼을 찾을 수 없습니다.");
  }
}
