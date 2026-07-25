package com.nextdv.infrastructure.channel;

import static org.assertj.core.api.Assertions.assertThat;

import com.nextdv.domain.channel.ChannelType;
import org.junit.jupiter.api.Test;

class ChannelTypeEntityTest {

  @Test
  void domain_타입을_infra_타입으로_변환한다() {
    for (ChannelType domainType : ChannelType.values()) {
      ChannelTypeEntity infraType = ChannelTypeEntity.valueOf(domainType.name());
      assertThat(infraType.name()).isEqualTo(domainType.name());
    }
  }

  @Test
  void infra_타입을_domain_타입으로_변환한다() {
    for (ChannelTypeEntity infraType : ChannelTypeEntity.values()) {
      ChannelType domainType = ChannelType.valueOf(infraType.name());
      assertThat(domainType.name()).isEqualTo(infraType.name());
    }
  }

  @Test
  void domain과_infra의_열거값이_동일하다() {
    assertThat(ChannelTypeEntity.values()).hasSameSizeAs(ChannelType.values());
  }
}
