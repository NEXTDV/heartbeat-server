package com.nextdv.infrastructure.healthcheck;

import static org.assertj.core.api.Assertions.assertThat;

import com.nextdv.domain.healthcheck.ServiceStatus;
import org.junit.jupiter.api.Test;

class HttpPlatformHealthCheckerTest {

  private final HttpPlatformHealthChecker checker = new HttpPlatformHealthChecker(null);

  @Test
  void 응답이_2xx이고_임계값_미만이면_OPERATIONAL() {
    ServiceStatus status = checker.determineStatus(
        200,
        500,
        1000
    );
    assertThat(status).isEqualTo(ServiceStatus.OPERATIONAL);
  }

  @Test
  void 응답이_2xx이고_임계값_이상이면_DEGRADED() {
    ServiceStatus status = checker.determineStatus(
        200,
        1000,
        1000
    );
    assertThat(status).isEqualTo(ServiceStatus.DEGRADED);
  }

  @Test
  void 응답이_4xx이면_OPERATIONAL() {
    ServiceStatus status = checker.determineStatus(
        404,
        100,
        1000
    );
    assertThat(status).isEqualTo(ServiceStatus.OPERATIONAL);
  }

  @Test
  void 응답이_5xx이면_MAJOR_OUTAGE() {
    ServiceStatus status = checker.determineStatus(
        500,
        100,
        1000
    );
    assertThat(status).isEqualTo(ServiceStatus.MAJOR_OUTAGE);
  }
}
