package com.nextdv.domain.common;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedEpochGenerator;
import java.util.UUID;

/**
 * class: UuidV7 작성자: JBumLee
 *
 * 시간 순서가 보장되는 UUID v7 생성 유틸리티
 */
public final class UuidV7 {

  private static final TimeBasedEpochGenerator GENERATOR = Generators.timeBasedEpochGenerator();

  private UuidV7() {
  }

  /**
   * 메소드이름: generate
   *
   * @return: 생성된 UUID v7 값
   *
   *          시간 기반 정렬 가능한 UUID v7을 생성한다
   */
  public static UUID generate() {
    return GENERATOR.generate();
  }
}
