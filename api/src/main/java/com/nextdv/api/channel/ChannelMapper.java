package com.nextdv.api.channel;

import com.nextdv.domain.channel.Channel;
import java.util.List;

public class ChannelMapper {

  public static ChannelResponse toResponse(Channel channel) {
    return new ChannelResponse(
        channel.getId(),
        channel.getUserId(),
        channel.getType(),
        channel.getName(),
        channel.getConfig(),
        channel.getCreatedAt()
    );
  }

  public static List<ChannelResponse> toResponseList(List<Channel> channels) {
    return channels.stream().map(ChannelMapper::toResponse).toList();
  }
}
