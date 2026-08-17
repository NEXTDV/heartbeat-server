package com.nextdv.infrastructure.channel;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.nextdv.domain.channel.ChannelPlatform;
import com.nextdv.domain.channel.ChannelPlatformService;
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
@Import({ChannelPlatformRepositoryImpl.class, ChannelPlatformService.class})
@Testcontainers
class ChannelPlatformServiceTest {

  @Container
  @ServiceConnection
  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

  @Autowired
  private ChannelPlatformJpaRepository channelPlatformJpaRepository;

  @Autowired
  private ChannelPlatformService channelPlatformService;

  @Test
  void 채널과_플랫폼을_구독하면_저장하고_반환한다() {
    UUID channelId = UUID.randomUUID();
    UUID platformId = UUID.randomUUID();

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
    UUID channelId = UUID.randomUUID();
    UUID platformId = UUID.randomUUID();

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
  void 존재하지_않는_채널ID로_구독하면_예외가_발생한다() {
    assertThatThrownBy(
        () -> channelPlatformService.subscribe(
            UUID.randomUUID(),
            UUID.randomUUID()
        )
    )
        .isInstanceOf(NoSuchElementException.class)
        .hasMessage("채널을 찾을 수 없습니다.");
  }

  @Test
  void 이미_구독된_채널_플랫폼을_다시_구독하면_예외가_발생한다() {
    UUID channelId = UUID.randomUUID();
    UUID platformId = UUID.randomUUID();
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
}
