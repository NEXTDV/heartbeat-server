package com.nextdv.domain.channel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChannelRepository {

  Channel save(Channel channel);

  List<Channel> findAllByUserId(UUID userId);

  Optional<Channel> findById(UUID id);
}
