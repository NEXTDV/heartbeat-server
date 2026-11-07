package com.nextdv.domain.platform;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 클래스명: PlatformService
 * 작성자: JBumLee
 *
 * 플랫폼 조회 비즈니스 로직을 담당하는 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PlatformService {

  private final PlatformRepository platformRepository;

  /**
   * 메소드이름: findAll
   * 활성화 상태인 플랫폼만 조회한다
   *
   * @return 활성화된 플랫폼 목록
   */
  public List<Platform> findAll() {
    List<Platform> platforms = platformRepository.findAll(true);
    log.info(
        "플랫폼 전체 조회 — 건수: {}",
        platforms.size()
    );
    return platforms;
  }

  /**
   * 메소드이름: findById
   * ID로 플랫폼을 조회한다
   *
   * @param id 조회할 플랫폼 UUID
   * @return 플랫폼 객체 (없으면 empty)
   */
  public Optional<Platform> findById(UUID id) {
    Optional<Platform> platform = platformRepository.findById(id);
    if (platform.isEmpty()) {
      log.warn(
          "플랫폼 조회 결과 없음 — id: {}",
          id
      );
    }
    return platform;
  }
}
