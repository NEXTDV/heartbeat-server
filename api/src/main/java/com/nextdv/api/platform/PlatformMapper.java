package com.nextdv.api.platform;

import com.nextdv.domain.platform.Platform;
import java.util.List;

/**
 * class: PlatformMapper 작성자: JBumLee
 *
 * Platform 도메인 객체를 PlatformResponse DTO로 변환하는 매퍼
 */
public class PlatformMapper {

  private PlatformMapper() {
  }

  /**
   * 메소드이름: toResponse
   *
   * @parameter: platform - 변환할 플랫폼 도메인 객체
   * @return: PlatformResponse DTO
   *
   *          Platform 도메인 객체를 응답 DTO로 변환한다
   */
  public static PlatformResponse toResponse(Platform platform) {
    return new PlatformResponse(
        platform.getId(),
        platform.getName(),
        platform.getCategory(),
        platform.getHealthCheckUrl(),
        platform.getTimeoutMs(),
        platform.getDegradedThresholdMs(),
        platform.getIconUrl(),
        platform.isActive()
    );
  }

  /**
   * 메소드이름: toResponseList
   *
   * @parameter: platforms - 변환할 플랫폼 도메인 객체 목록
   * @return: PlatformResponse DTO 목록
   *
   *          Platform 도메인 객체 목록을 응답 DTO 목록으로 변환한다
   */
  public static List<PlatformResponse> toResponseList(List<Platform> platforms) {
    return platforms.stream()
        .map(PlatformMapper::toResponse)
        .toList();
  }
}
