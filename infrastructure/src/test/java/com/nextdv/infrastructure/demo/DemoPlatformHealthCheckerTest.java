package com.nextdv.infrastructure.demo;

import static org.assertj.core.api.Assertions.assertThat;

import com.nextdv.domain.healthcheck.ServiceStatus;
import com.nextdv.domain.platform.Platform;
import com.nextdv.domain.platform.ServiceCategory;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class DemoPlatformHealthCheckerTest {

  private final DemoPlatformHealthChecker checker = new DemoPlatformHealthChecker();

  @Test
  void demo_상태_계산_결과는_유효한_상태중_하나다() {
    ServiceStatus status = checker.computeStatus(12345L);
    assertThat(status).isIn(
        ServiceStatus.OPERATIONAL,
        ServiceStatus.DEGRADED,
        ServiceStatus.MAJOR_OUTAGE
    );
  }

  @Test
  void 같은_window_index는_항상_같은_상태를_반환한다() {
    ServiceStatus first = checker.computeStatus(99L);
    ServiceStatus second = checker.computeStatus(99L);
    assertThat(first).isEqualTo(second);
  }

  @Test
  void demo_URL_플랫폼은_supports가_true다() {
    Platform platform = new Platform(
        UUID.randomUUID(), "Test서버", ServiceCategory.OTHER,
        "demo://test", 10000, 1000, null, true
    );
    assertThat(checker.supports(platform)).isTrue();
  }

  @Test
  void 일반_URL_플랫폼은_supports가_false다() {
    Platform platform = new Platform(
        UUID.randomUUID(), "GitHub", ServiceCategory.DEVTOOL,
        "https://github.com", 5000, 2000, null, true
    );
    assertThat(checker.supports(platform)).isFalse();
  }
}
