package com.nextdv.api.channel;

import com.nextdv.api.common.CommonResponse;
import com.nextdv.domain.channel.ChannelPlatform;
import com.nextdv.domain.channel.ChannelPlatformService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
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
  @ApiResponse(responseCode = "400", description = "channelId 또는 platformId 누락 또는 UUID 형식이 아님")
  @ApiResponse(responseCode = "404", description = "해당 channelId 또는 platformId에 해당하는 리소스가 존재하지 않음")
  @ApiResponse(responseCode = "409", description = "해당 채널과 플랫폼 조합이 이미 구독 중")
  @ApiResponse(responseCode = "500", description = "서버 내부 오류")
  public ResponseEntity<CommonResponse<ChannelPlatformResponse>> subscribe(
      @Valid @RequestBody ChannelPlatformRequest request) {
    ChannelPlatform channelPlatform = channelPlatformService.subscribe(
        request.getChannelId(),
        request.getPlatformId()
    );
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(CommonResponse.ok(ChannelPlatformResponse.from(channelPlatform)));
  }
}
