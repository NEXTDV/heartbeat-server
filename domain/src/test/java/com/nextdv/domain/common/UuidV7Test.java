package com.nextdv.domain.common;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.Test;

class UuidV7Test {

  @Test
  void UUID_v7을_생성한다() {
    UUID uuid = UuidV7.generate();

    assertThat(uuid).isNotNull();
  }

  @Test
  void 생성된_UUID는_버전7이다() {
    UUID uuid = UuidV7.generate();

    assertThat(uuid.version()).isEqualTo(7);
  }

  @Test
  void 연속_생성된_UUID는_시간순으로_정렬된다() {
    UUID first = UuidV7.generate();
    UUID second = UuidV7.generate();

    assertThat(first.toString().compareTo(second.toString())).isLessThanOrEqualTo(0);
  }

  @Test
  void 생성된_UUID는_매번_다르다() {
    UUID first = UuidV7.generate();
    UUID second = UuidV7.generate();

    assertThat(first).isNotEqualTo(second);
  }
}
