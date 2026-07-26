package com.nextdv.domain.channel;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ChannelServiceTest {

  @Mock
  private ChannelRepository channelRepository;

  @InjectMocks
  private ChannelService channelService;

  @Test
  void 채널을_생성하면_저장하고_반환한다() {
    UUID userId = UUID.randomUUID();
    Map<String, Object> config = Map.of(
        "webhook",
        "https://example.com"
    );
    Channel saved = new Channel(
        UUID.randomUUID(),
        userId,
        ChannelType.SLACK,
        "내 슬랙",
        config,
        Instant.now(),
        Instant.now(),
        null
    );
    given(channelRepository.save(any())).willReturn(saved);

    Channel result = channelService.create(
        userId,
        ChannelType.SLACK,
        "내 슬랙",
        config
    );

    assertThat(result.getUserId()).isEqualTo(userId);
    assertThat(result.getType()).isEqualTo(ChannelType.SLACK);
    assertThat(result.getDeletedAt()).isNull();
    then(channelRepository).should().save(any());
  }

  @Test
  void userId로_채널_목록을_조회한다() {
    UUID userId = UUID.randomUUID();
    List<Channel> channels = List.of(
        new Channel(
            UUID.randomUUID(),
            userId,
            ChannelType.DISCORD,
            "디스코드",
            Map.of(),
            Instant.now(),
            Instant.now(),
            null
        )
    );
    given(channelRepository.findAllByUserId(userId)).willReturn(channels);

    List<Channel> result = channelService.findAllByUserId(userId);

    assertThat(result).hasSize(1);
    assertThat(result.get(0).getUserId()).isEqualTo(userId);
  }

  @Test
  void 채널을_삭제하면_deletedAt이_설정된다() {
    UUID channelId = UUID.randomUUID();
    Channel existing = new Channel(
        channelId,
        UUID.randomUUID(),
        ChannelType.EMAIL,
        "이메일",
        Map.of(),
        Instant.now(),
        Instant.now(),
        null
    );
    given(channelRepository.findById(channelId)).willReturn(Optional.of(existing));
    given(channelRepository.save(any())).willAnswer(inv -> inv.getArgument(0));

    channelService.delete(channelId);

    ArgumentCaptor<Channel> captor = ArgumentCaptor.forClass(Channel.class);
    then(channelRepository).should().save(captor.capture());
    assertThat(captor.getValue().getDeletedAt()).isNotNull();
  }

  @Test
  void 존재하지_않는_채널을_삭제하면_예외가_발생한다() {
    UUID channelId = UUID.randomUUID();
    given(channelRepository.findById(channelId)).willReturn(Optional.empty());

    assertThatThrownBy(() -> channelService.delete(channelId))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("채널을 찾을 수 없습니다.");
  }
}
