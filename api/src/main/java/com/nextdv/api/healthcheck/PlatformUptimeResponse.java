package com.nextdv.api.healthcheck;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 클래스명: PlatformUptimeResponse
 * 작성자: JBumLee
 *
 * 플랫폼 가동률 조회 응답 DTO
 */
@Getter
@AllArgsConstructor
public class PlatformUptimeResponse {

  private UUID platformId;
  private int windowHours;
  private Double uptimePercent;
}
