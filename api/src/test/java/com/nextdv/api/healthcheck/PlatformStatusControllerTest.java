package com.nextdv.api.healthcheck;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.nextdv.domain.healthcheck.HealthCheckLog;
import com.nextdv.domain.healthcheck.HealthCheckLogService;
import com.nextdv.domain.healthcheck.ServiceStatus;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class PlatformStatusControllerTest {

  private MockMvc mockMvc;

  @Mock
  private HealthCheckLogService healthCheckLogService;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(
        new PlatformStatusController(healthCheckLogService)
    ).build();
  }

  @Test
  void 최신_상태가_있으면_200과_데이터를_반환한다() throws Exception {
    UUID platformId = UUID.randomUUID();
    HealthCheckLog log = new HealthCheckLog(
        UUID.randomUUID(), platformId, ServiceStatus.OPERATIONAL, 300L, Instant.now()
    );
    given(healthCheckLogService.findLatestByPlatformId(platformId)).willReturn(Optional.of(log));

    mockMvc.perform(
        get(
            "/platforms/{id}/status",
            platformId
        )
    )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.data.status").value("OPERATIONAL"));
  }

  @Test
  void 최신_상태가_없으면_200과_null_데이터를_반환한다() throws Exception {
    UUID platformId = UUID.randomUUID();
    given(healthCheckLogService.findLatestByPlatformId(platformId)).willReturn(Optional.empty());

    mockMvc.perform(
        get(
            "/platforms/{id}/status",
            platformId
        )
    )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.data").doesNotExist());
  }

  @Test
  void 로그_목록을_조회하면_200과_리스트를_반환한다() throws Exception {
    UUID platformId = UUID.randomUUID();
    List<HealthCheckLog> logs = List.of(
        new HealthCheckLog(
            UUID.randomUUID(), platformId, ServiceStatus.OPERATIONAL, 200L, Instant.now()
        ),
        new HealthCheckLog(
            UUID.randomUUID(), platformId, ServiceStatus.DEGRADED, 1500L, Instant.now()
        )
    );
    given(healthCheckLogService.findAllByPlatformId(platformId)).willReturn(logs);

    mockMvc.perform(
        get(
            "/platforms/{id}/logs",
            platformId
        )
    )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.data.length()").value(2));
  }
}
