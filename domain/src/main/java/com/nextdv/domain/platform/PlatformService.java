package com.nextdv.domain.platform;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * class: PlatformService 작성자: JBumLee
 *
 * 플랫폼 조회 비즈니스 로직을 담당하는 서비스
 */
@Service
@RequiredArgsConstructor
public class PlatformService {

  private final PlatformRepository platformRepository;

  /**
   * 메소드이름: findAll
   *
   * @return: 활성화된 플랫폼 목록
   *
   *          활성화 상태인 플랫폼만 조회한다
   */
  public List<Platform> findAll() {
    return platformRepository.findAll(true);
  }

  /**
   * 메소드이름: findById
   *
   * @parameter: id - 조회할 플랫폼 UUID
   * @return: 플랫폼 객체 (없으면 empty)
   *
   *          ID로 플랫폼을 조회한다
   */
  public Optional<Platform> findById(UUID id) {
    return platformRepository.findById(id);
  }
}
