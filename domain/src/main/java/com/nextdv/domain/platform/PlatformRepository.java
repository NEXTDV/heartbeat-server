package com.nextdv.domain.platform;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * 클래스명: PlatformRepository
 * 작성자: JBumLee
 *
 * 플랫폼 도메인 저장소 인터페이스
 */
public interface PlatformRepository {

  /**
   * 메소드이름: findAll
   * 전체 또는 활성화된 플랫폼 목록을 조회한다
   *
   * @param activeOnly true이면 활성화된 플랫폼만 조회
   * @return 플랫폼 목록
   */
  List<Platform> findAll(boolean activeOnly);

  /**
   * 메소드이름: findById
   * ID로 플랫폼을 조회한다
   *
   * @param id 조회할 플랫폼 UUID
   * @return 플랫폼 객체 (없으면 empty)
   */
  Optional<Platform> findById(UUID id);
}
