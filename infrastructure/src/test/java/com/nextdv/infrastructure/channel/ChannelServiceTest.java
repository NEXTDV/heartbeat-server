package com.nextdv.infrastructure.channel;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.nextdv.domain.channel.Channel;
import com.nextdv.domain.channel.ChannelService;
import com.nextdv.domain.channel.ChannelType;
import java.time.Instant;
import java.util.List;
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
@Import({ChannelRepositoryImpl.class, ChannelService.class})
@Testcontainers
class ChannelServiceTest {

  @Container
  @ServiceConnection
  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

  @Autowired
  private ChannelJpaRepository channelJpaRepository;

  @Autowired
  private ChannelService channelService;

  @Test
  void 채널을_생성하면_저장하고_반환한다() {
    UUID userId = UUID.randomUUID();
    Map<String, Object> config = Map.of(
        "url",
        "https://hooks.slack.com/example"
    );

    Channel result = channelService.create(
        userId,
        ChannelType.SLACK,
        "내 슬랙",
        config
    );

    assertThat(result.getId()).isNotNull();
    assertThat(result.getUserId()).isEqualTo(userId);
    assertThat(result.getType()).isEqualTo(ChannelType.SLACK);
    assertThat(result.getDeletedAt()).isNull();
  }

  @Test
  void userId로_채널_목록을_조회한다() {
    UUID userId = UUID.randomUUID();
    Instant now = Instant.now();
    channelJpaRepository.save(
        new ChannelEntity(
            UUID.randomUUID(), userId, ChannelTypeEntity.SLACK, "슬랙", Map.of(), now, now, null
        )
    );
    channelJpaRepository.save(
        new ChannelEntity(
            UUID.randomUUID(), userId, ChannelTypeEntity.DISCORD, "디스코드", Map.of(), now, now,
            null
        )
    );

    List<Channel> result = channelService.findAllByUserId(userId);

    assertThat(result).hasSize(2);
    assertThat(result).extracting(Channel::getUserId).containsOnly(userId);
  }

  @Test
  void 채널을_삭제하면_deletedAt이_설정된다() {
    UUID userId = UUID.randomUUID();
    Channel created = channelService.create(
        userId,
        ChannelType.EMAIL,
        "이메일",
        Map.of(
            "address",
            "test@example.com"
        )
    );

    channelService.delete(created.getId());

    ChannelEntity entity = channelJpaRepository.findById(created.getId()).orElseThrow();
    assertThat(entity.getDeletedAt()).isNotNull();
  }

  @Test
  void 삭제된_채널은_목록_조회에서_제외된다() {
    UUID userId = UUID.randomUUID();
    Channel active = channelService.create(
        userId,
        ChannelType.SLACK,
        "활성 채널",
        Map.of(
            "url",
            "https://hooks.slack.com/active"
        )
    );
    Channel deleted = channelService.create(
        userId,
        ChannelType.DISCORD,
        "삭제된 채널",
        Map.of(
            "url",
            "https://discord.com/api/webhooks/deleted"
        )
    );
    channelService.delete(deleted.getId());

    List<Channel> result = channelService.findAllByUserId(userId);

    assertThat(result).hasSize(1);
    assertThat(result.get(0).getId()).isEqualTo(active.getId());
  }

  @Test
  void 존재하지_않는_채널을_삭제하면_예외가_발생한다() {
    UUID channelId = UUID.randomUUID();

    assertThatThrownBy(() -> channelService.delete(channelId))
        .isInstanceOf(NoSuchElementException.class)
        .hasMessage("채널을 찾을 수 없습니다.");
  }

  @Test
  void EMAIL_채널_config에_address가_없으면_예외가_발생한다() {
    assertThatThrownBy(
        () -> channelService.create(
            UUID.randomUUID(),
            ChannelType.EMAIL,
            "이메일",
            Map.of()
        )
    )
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("EMAIL 채널은 config.address가 필요합니다.");
  }

  @Test
  void SLACK_채널_config에_url이_없으면_예외가_발생한다() {
    assertThatThrownBy(
        () -> channelService.create(
            UUID.randomUUID(),
            ChannelType.SLACK,
            "슬랙",
            Map.of()
        )
    )
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("SLACK 채널은 config.url이 필요합니다.");
  }

  @Test
  void DISCORD_채널_config에_url이_없으면_예외가_발생한다() {
    assertThatThrownBy(
        () -> channelService.create(
            UUID.randomUUID(),
            ChannelType.DISCORD,
            "디스코드",
            Map.of()
        )
    )
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("DISCORD 채널은 config.url이 필요합니다.");
  }
}
