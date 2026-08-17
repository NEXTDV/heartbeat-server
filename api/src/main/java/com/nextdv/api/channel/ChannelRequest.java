package com.nextdv.api.channel;

import com.nextdv.domain.channel.ChannelType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Map;
import java.util.UUID;
import lombok.Getter;

@Getter
public class ChannelRequest {

  @NotNull(message = "userId는 필수입니다.")
  private UUID userId;

  @NotNull(message = "type은 필수입니다.")
  private ChannelType type;

  @NotBlank(message = "name은 필수입니다.")
  private String name;

  @NotNull(message = "config는 필수입니다.")
  private Map<String, Object> config;
}
