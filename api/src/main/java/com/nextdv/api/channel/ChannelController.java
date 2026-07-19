package com.nextdv.api.channel;

import com.nextdv.api.common.ApiResponse;
import com.nextdv.domain.channel.Channel;
import com.nextdv.domain.channel.ChannelService;
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
  public ResponseEntity<ApiResponse<ChannelResponse>> create(@RequestBody ChannelRequest request) {
    Channel channel = channelService.create(
        request.getUserId(),
        request.getType(),
        request.getName(),
        request.getConfig()
    );
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.ok(ChannelMapper.toResponse(channel)));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<ChannelResponse>>> list(
      @RequestParam UUID userId) {
    List<Channel> channels = channelService.findAllByUserId(userId);
    return ResponseEntity.ok(ApiResponse.ok(ChannelMapper.toResponseList(channels)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
    channelService.delete(id);
    return ResponseEntity.ok(ApiResponse.ok(null));
  }
}
