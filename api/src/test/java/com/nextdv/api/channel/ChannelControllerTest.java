package com.nextdv.api.channel;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nextdv.domain.channel.Channel;
import com.nextdv.domain.channel.ChannelService;
import com.nextdv.domain.channel.ChannelType;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class ChannelControllerTest {

  private MockMvc mockMvc;
  private ObjectMapper objectMapper;

  @Mock
  private ChannelService channelService;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(new ChannelController(channelService)).build();
    objectMapper = new ObjectMapper();
  }

  @Test
  void 채널을_생성하면_201과_생성된_채널을_반환한다() throws Exception {
    UUID userId = UUID.randomUUID();
    UUID channelId = UUID.randomUUID();
    Channel created = new Channel(
        channelId, userId, ChannelType.SLACK, "슬랙 알림", Map.of(), Instant.now(), Instant.now(), null
    );
    given(
        channelService.create(
            any(),
            any(),
            any(),
            any()
        )
    ).willReturn(created);

    String body = objectMapper.writeValueAsString(
        Map.of(
            "userId",
            userId,
            "type",
            "SLACK",
            "name",
            "슬랙 알림",
            "config",
            Map.of()
        )
    );

    mockMvc.perform(post("/channels").contentType(MediaType.APPLICATION_JSON).content(body))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.data.name").value("슬랙 알림"));
  }

  @Test
  void userId로_채널_목록을_조회한다() throws Exception {
    UUID userId = UUID.randomUUID();
    List<Channel> channels = List.of(
        new Channel(
            UUID.randomUUID(), userId, ChannelType.SLACK, "슬랙", Map.of(), Instant.now(),
            Instant.now(), null
        ),
        new Channel(
            UUID.randomUUID(), userId, ChannelType.DISCORD, "디스코드", Map.of(), Instant.now(),
            Instant.now(), null
        )
    );
    given(channelService.findAllByUserId(userId)).willReturn(channels);

    mockMvc.perform(
        get("/channels").param(
            "userId",
            userId.toString()
        )
    )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.data.length()").value(2));
  }

  @Test
  void 채널을_삭제하면_200을_반환한다() throws Exception {
    UUID channelId = UUID.randomUUID();
    willDoNothing().given(channelService).delete(channelId);

    mockMvc.perform(
        delete(
            "/channels/{id}",
            channelId
        )
    )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true));
  }
}
