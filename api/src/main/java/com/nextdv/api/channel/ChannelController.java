package com.nextdv.api.channel;

import com.nextdv.api.common.CommonResponse;
import com.nextdv.domain.channel.Channel;
import com.nextdv.domain.channel.ChannelService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/channels")
public class ChannelController {

  private final ChannelService channelService;

  public ChannelController(ChannelService channelService) {
    this.channelService = channelService;
  }

  @PostMapping
  @ApiResponse(responseCode = "400", description = "userId, type, name, config 필수 필드 누락 또는 형식 오류")
  @ApiResponse(responseCode = "500", description = "서버 내부 오류")
  public ResponseEntity<CommonResponse<ChannelResponse>> create(
      @Valid @RequestBody ChannelRequest request) {
    Channel channel = channelService.create(
        request.getUserId(),
        request.getType(),
        request.getName(),
        request.getConfig()
    );
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(CommonResponse.ok(ChannelMapper.toResponse(channel)));
  }

  @GetMapping
  @ApiResponse(responseCode = "400", description = "userId 파라미터 누락 또는 UUID 형식이 아님")
  @ApiResponse(responseCode = "500", description = "서버 내부 오류")
  public ResponseEntity<CommonResponse<List<ChannelResponse>>> list(@RequestParam UUID userId) {
    List<Channel> channels = channelService.findAllByUserId(userId);
    return ResponseEntity.ok(CommonResponse.ok(ChannelMapper.toResponseList(channels)));
  }

  @DeleteMapping("/{id}")
  @ApiResponse(responseCode = "400", description = "id가 UUID 형식이 아님")
  @ApiResponse(responseCode = "404", description = "해당 ID에 해당하는 채널이 존재하지 않음")
  @ApiResponse(responseCode = "500", description = "서버 내부 오류")
  public ResponseEntity<CommonResponse<Void>> delete(@PathVariable UUID id) {
    channelService.delete(id);
    return ResponseEntity.ok(CommonResponse.ok(null));
  }
}
