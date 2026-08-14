package com.nextdv.api.channel;

import com.nextdv.api.common.ApiResponse;
import com.nextdv.domain.channel.ChannelPlatform;
import com.nextdv.domain.channel.ChannelPlatformService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/channel-platforms")
public class ChannelPlatformController {

  private final ChannelPlatformService channelPlatformService;

  public ChannelPlatformController(ChannelPlatformService channelPlatformService) {
    this.channelPlatformService = channelPlatformService;
  }

  @PostMapping
  public ResponseEntity<ApiResponse<ChannelPlatformResponse>> subscribe(
      @RequestBody ChannelPlatformRequest request) {
    ChannelPlatform channelPlatform = channelPlatformService.subscribe(
        request.getChannelId(),
        request.getPlatformId()
    );
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.ok(ChannelPlatformResponse.from(channelPlatform)));
  }
}
