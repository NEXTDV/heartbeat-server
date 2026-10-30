package com.nextdv.api.channel;

import com.nextdv.api.common.CommonResponse;
import com.nextdv.domain.channel.ChannelPlatform;
import com.nextdv.domain.channel.ChannelPlatformService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 클래스명: ChannelPlatformController
 * 작성자: JBumLee
 *
 * 채널-플랫폼 구독 관련 REST API 엔드포인트를 제공하는 컨트롤러
 */
@Slf4j
@RestController
@RequestMapping("/channel-platforms")
public class ChannelPlatformController {

  private final ChannelPlatformService channelPlatformService;

  public ChannelPlatformController(ChannelPlatformService channelPlatformService) {
    this.channelPlatformService = channelPlatformService;
  }

  /**
   * 메소드이름: subscribe
   * 채널과 플랫폼을 연결하는 구독을 생성한다
   *
   * @param request 구독 요청 (channelId, platformId 포함)
   * @return 생성된 구독 정보 응답
   */
  @PostMapping
  @ApiResponse(responseCode = "400", description = "channelId 또는 platformId 누락 또는 UUID 형식이 아님")
  @ApiResponse(responseCode = "404", description = "해당 channelId 또는 platformId에 해당하는 리소스가 존재하지 않음")
  @ApiResponse(responseCode = "409", description = "해당 채널과 플랫폼 조합이 이미 구독 중")
  @ApiResponse(responseCode = "500", description = "서버 내부 오류")
  public ResponseEntity<CommonResponse<ChannelPlatformResponse>> subscribe(
      @Valid @RequestBody ChannelPlatformRequest request) {
    log.info(
        "구독 요청 — channelId: {}, platformId: {}",
        request.getChannelId(),
        request.getPlatformId()
    );
    ChannelPlatform channelPlatform = channelPlatformService.subscribe(
        request.getChannelId(),
        request.getPlatformId()
    );
    log.info(
        "구독 완료 — id: {}",
        channelPlatform.getId()
    );
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(CommonResponse.ok(ChannelPlatformResponse.from(channelPlatform)));
  }
}
